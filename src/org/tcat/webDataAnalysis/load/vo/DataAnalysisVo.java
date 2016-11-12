package org.tcat.webDataAnalysis.load.vo;

import org.tcat.tools.StringTools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lin on 2016/5/30.
 */
public class DataAnalysisVo {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;
    /**
     * 继承于某个id
     */
    private String extendsInId;
    /**
     * 查询条件
     */
    private String select;
    /**
     * 值类型
     */
    private String valueType;
    /**
     * xls列数
     */
    private String column;
    /**
     * 列名
     */
    private String columnName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtendsInId() {
        return extendsInId;
    }

    public void setExtendsInId(String extendsInId) {
        this.extendsInId = extendsInId;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {

        StringBuffer content = new StringBuffer();
        content.append("DataAnalysisVo{");
        List<StringBuffer> dataList = new ArrayList<>();
        if (id != null) {
            dataList.add(new StringBuffer().append("id='").append(id).append("'"));
        }
        if (extendsInId != null) {
            dataList.add(new StringBuffer().append("extendsInId='").append(extendsInId).append("'"));
        }
        if (select != null) {
            dataList.add(new StringBuffer().append("select='").append(select).append("'"));
        }
        if (valueType != null) {
            dataList.add(new StringBuffer().append("valueType='").append(valueType).append("'"));
        }
        if (column != null) {
            dataList.add(new StringBuffer().append("column='").append(column).append("'"));
        }
        if (columnName != null) {
            dataList.add(new StringBuffer().append("columnName='").append(columnName).append("'"));
        }
        dataList.add(new StringBuffer().append("priority='").append(getPriority()).append("'"));
        return content.append(dataList.stream().collect(Collectors.joining(","))).append("}").toString();

    }

    /**
     * 获取优先级
     *
     * @return
     */
    public Integer getPriority() {
        if (!StringTools.isNotEmptyByTrim(id) && StringTools.isNotEmptyByTrim(extendsInId)) {
            //有id无extends
            return 1;
        } else if (!StringTools.isNotEmptyByTrim(id) && !StringTools.isNotEmptyByTrim(extendsInId)) {
            //有extends有id
            return 2;
        } else if (StringTools.isNotEmptyByTrim(id) && !StringTools.isNotEmptyByTrim(extendsInId)) {
            //有extends无id
            return 3;
        } else if (StringTools.isNotEmptyByTrim(id) && StringTools.isNotEmptyByTrim(extendsInId)) {
            //无extens无id
            return 4;
        }
        return null;
    }

}
