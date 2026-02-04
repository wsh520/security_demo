package com.wl.security_demo.handles;

import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import com.wl.security_demo.domain.entity.SysRole;
import com.wl.security_demo.utils.SecurityUtils;
import com.wl.security_demo.vo.LoginUser;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import org.springframework.stereotype.Component;

@Component
public class DataScopeHandler implements MultiDataPermissionHandler {

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        // 1. 确定哪些表需要进行数据过滤（防止对系统表也进行过滤）
        String tableName = table.getName();
        if (!"biz_order".equalsIgnoreCase(tableName)) { // 也可以根据表名前缀判断
            return null;
        }

        // 2. 获取当前登录用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();

        // 3. 如果是超级管理员，或者未登录，直接放行不处理
        if (loginUser == null || SecurityUtils.isAdmin()) {
            return null;
        }

        // 4. 获取当前用户的“最大数据范围”
        // 1:全部, 3:本部门, 4:本部门及以下, 5:仅本人
        Integer dataScope = 5;

        // 5. 根据范围构造过滤表达式
        String filterSql = buildFilterSql(table, loginUser, dataScope);
        if (filterSql == null) return where;

        try {
            // 将 SQL 字符串解析为 Expression 对象
            return CCJSqlParserUtil.parseCondExpression(filterSql);
        } catch (JSQLParserException e) {
            throw new RuntimeException("数据权限 SQL 解析异常", e);
        }
    }

    private String buildFilterSql(Table table, LoginUser user, Integer scope) {
        // 获取表别名（如果有），防止多表 Join 时字段冲突
        String alias = table.getAlias() != null ? table.getAlias().getName() : table.getName();

        switch (scope) {
            case 1: return null; // 全部数据，不拼接
            case 3: // 本部门
                return String.format("%s.dept_id = %d", alias, user.getDeptId());
            case 4: // 本部门及以下 (假设 sys_dept 表有 ancestors 字段)
                return String.format("%s.dept_id IN (SELECT id FROM sys_dept WHERE FIND_IN_SET(%d, ancestors) OR id = %d)",
                        alias, user.getDeptId(), user.getDeptId());
            case 5: // 仅本人
                return String.format("%s.user_id = %d", alias, user.getUserId());
            default:
                return "1 = 0"; // 无权限时返回一个必然失败的条件
        }
    }
}
