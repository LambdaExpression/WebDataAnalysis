package org.tcat.tools;

import org.tcat.tools.vo.ListMapCriteriaVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lin on 2016/5/23.
 */
public class ListTools {

    /**
     * 从data找出符合criteria的条件参数
     * @param data
     * @param criteria
     * @return
     */
    public static List<Map<String, Object>> findListMapCriterias(List<Map<String, Object>> data, List<ListMapCriteriaVo> criteria) {
        if (criteria == null || criteria.size() == 0) {
            return data;
        }
        List<Map<String, Object>> newData = new ArrayList<>();
        data.forEach(d -> {
            boolean flag = true;
            for (ListMapCriteriaVo lmc : criteria) {
                if (!lmc.getValue().equals(d.get(lmc.getName())))
                    flag = false;
            }
            if (flag)
                newData.add(d);
        });
        return newData;
    }

}
