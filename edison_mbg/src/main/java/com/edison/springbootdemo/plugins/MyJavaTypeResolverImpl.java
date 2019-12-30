package com.edison.springbootdemo.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**这是针对不同类型的数据库字段生成不同的java类型对象；
 * 这里主要是为了将默认的长度小于4的字段的对象类型替换为int，而不是默认的short*/
public class MyJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {
    boolean smallintToInteger;

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.smallintToInteger=StringUtility.isTrue(properties.getProperty("smallintToInteger"));
        if(this.smallintToInteger) {//数据库字段如果定义成smallint，则会被当做int处理
            super.typeMap.put(5, new JavaTypeResolverDefaultImpl.JdbcTypeInformation("INTEGER", new FullyQualifiedJavaType(Integer.class.getName())));
        }
    }

    public MyJavaTypeResolverImpl(){
        super();
        System.out.println("smallintToInteger="+smallintToInteger);//此时addConfigurationProperties还没执行，这里是false
    }

    @Override
    protected FullyQualifiedJavaType calculateBigDecimalReplacement(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
        System.out.println(typeMap.get(5).getFullyQualifiedJavaType()+" "+typeMap.get(5).getJdbcTypeName());
        FullyQualifiedJavaType answer;
        if(column.getActualColumnName().equalsIgnoreCase("misfire_Instr")){
            System.out.println(column.getScale()+" "+column.getLength()+" "+!this.forceBigDecimals);
        }
        if (column.getScale() <= 0 && column.getLength() <= 18 && !this.forceBigDecimals) {
            if (column.getLength() > 9) {
                answer = new FullyQualifiedJavaType(Long.class.getName());
            } /*else if (column.getLength() > 4) {
                answer = new FullyQualifiedJavaType(Integer.class.getName());
            } else {//默认小于等于4位的数据就定义成short
                answer = new FullyQualifiedJavaType(Short.class.getName());
            }*/else{
                answer = new FullyQualifiedJavaType(Integer.class.getName());
            }
        } else {
            answer = defaultType;
        }

        return answer;
    }
}
