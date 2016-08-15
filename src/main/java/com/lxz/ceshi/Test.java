/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.ceshi;

import jodd.util.StringUtil;
import org.springframework.util.StringUtils;

import com.lxz.util.JsonUtil;

/**
 * @author: xiaole  Date: 14-7-31 Time: 下午5:03
 * @version: \$Id$
 */
public class Test {
    static String sql = "select total_count.user_id groupId, total_count.real_name name,\n" +
            "total_count.total_visited totalVisited, by_door_count.by_door_visited\n" +
            "byDoorVisited, kp_count.KP_visited KPVisited,\n" +
            "by_door_kp_count.by_door_kp_visited byDoorKPVisited from (select\n" +
            "count(*) total_visited, visit_record.user_id user_id, user.realname\n" +
            "real_name from qadmin_org org left join qadmin_user user on user.org_id\n" +
            "= org.id left join crm_visit_record visit_record on visit_record.user_id\n" +
            "= user.user_id WHERE org.tree_code like concat((select tree_code from\n" +
            "qadmin_org where id=?), '%') and from_unixtime(visit_record.visit_time /\n" +
            "1000) >= ? and from_unixtime(visit_record.visit_time / 1000) <= ? group\n" +
            "by visit_record.user_id) total_count left join (select count(*)\n" +
            "by_door_visited, visit_record.user_id user_id, user.realname real_name\n" +
            "from qadmin_org org left join qadmin_user user on user.org_id = org.id\n" +
            "left join crm_visit_record visit_record on visit_record.user_id =\n" +
            "user.user_id WHERE visit_record.visit_type = 2 and org.tree_code like\n" +
            "concat((select tree_code from qadmin_org where id=?), '%') and\n" +
            "from_unixtime(visit_record.visit_time / 1000) >= ? and\n" +
            "from_unixtime(visit_record.visit_time / 1000) <= ? group by\n" +
            "visit_record.user_id) by_door_count on total_count.user_id =\n" +
            "by_door_count.user_id left join (select count(*) kp_visited,\n" +
            "visit_record.user_id user_id, user.realname real_name from qadmin_org\n" +
            "org left join qadmin_user user on user.org_id = org.id left join\n" +
            "crm_visit_record visit_record on visit_record.user_id = user.user_id\n" +
            "WHERE visit_record.is_active = 0 and org.tree_code like concat((select\n" +
            "tree_code from qadmin_org where id=?), '%') and\n" +
            "from_unixtime(visit_record.visit_time / 1000) >= ? and\n" +
            "from_unixtime(visit_record.visit_time / 1000) <= ? group by\n" +
            "visit_record.user_id) kp_count on total_count.user_id = kp_count.user_id\n" +
            "left join (select count(*) by_door_kp_visited, visit_record.user_id\n" +
            "user_id, user.realname real_name from qadmin_org org left join\n" +
            "qadmin_user user on user.org_id = org.id left join crm_visit_record\n" +
            "visit_record on visit_record.user_id = user.user_id WHERE\n" +
            "visit_record.is_active = 0 and visit_record.visit_type = 2 and\n" +
            "org.tree_code like concat((select tree_code from qadmin_org where id=?),\n" +
            "'%') and from_unixtime(visit_record.visit_time / 1000) >= ? and\n" +
            "from_unixtime(visit_record.visit_time / 1000) <= ? group by\n" +
            "visit_record.user_id) by_door_kp_count on total_count.user_id =\n" +
            "by_door_kp_count.user_id order by ? ? limit 10";

    static String params = "'101','2014-04-01 00:00:00','2014-08-09 23:59:59','101','2014-04-01 00:00:00','2014-08-09 23:59:59','101','2014-04-01 00:00:00','2014-08-09 23:59:59','101','2014-04-01 00:00:00','2014-08-09 23:59:59','totalVisited','desc'";


    public static void main(String[] args){
        String[] param = StringUtils.split(params, ",");
        System.out.println(JsonUtil.encode(param));

        String innerSql = sql;

        for(String p: param){
            innerSql = StringUtil.replaceFirst(innerSql,"?",p);
        }

        //System.out.println(innerSql);

        System.out.println(OperateType.ADD.ordinal());


        System.out.println(String.format("你好啊, %s, %s,%s", 1,2,"jim"));
    }

    private static enum OperateType{
        CHECK,ADD;
    }
}
