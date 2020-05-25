package com.edison.springbootdemo.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;
import java.util.Set;

/**
 * 自定义注释生成器
 * Created by macro on 2018/4/26.
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = true;
    private static final String EXAMPLE_SUFFIX="Example";
//    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME="io.swagger.annotations.ApiModelProperty";
//    private static final String COMPONENT_CLASS_NAME="org.springframework.stereotype.Repository";
//    private static final String MAPPER_CLASS_NAME="org.apache.ibatis.annotations.Mapper";

    /**
     * 设置用户配置的参数
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 给字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加备注信息
        if(addRemarkComments&&StringUtility.stringHasValue(remarks)){
            addFieldJavaDoc(field, remarks);
            //数据库中特殊字符需要转义
            if(remarks.contains("\"")){
                remarks = remarks.replace("\"","'");
            }
            //给model的字段添加swagger注解
//            field.addJavaDocLine("@ApiModelProperty(value = \""+remarks+"\")");
        }
    }

    /**给实体类文件import一些类*/
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        //只在model中添加swagger注解类的导入
        if(!compilationUnit.isJavaInterface()&&!compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)){
//            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }
//        compilationUnit.addImportedType(new FullyQualifiedJavaType(MAPPER_CLASS_NAME));
//        compilationUnit.addImportedType(new FullyQualifiedJavaType(COMPONENT_CLASS_NAME));
    }

    /**给get和set方法设置中文说明*/
    @Override
    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            method.addJavaDocLine("/**获取" + introspectedColumn.getRemarks()+" */");
//            log.info(introspectedColumn.getActualColumnName()+"类型"+introspectedColumn.getFullyQualifiedJavaType()); //返回java.lang.String一类的全路径，可以做处理
//            log.info(introspectedColumn.getActualColumnName()+"类型"+introspectedColumn.getJavaProperty()); //这个是返回java实体类中的field，比如 EM_ID对应emId
//            log.info(introspectedColumn.getActualColumnName()+"类型"+introspectedColumn.));


        }
    }

    /**给实体类添加注释和注解*/
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**"+introspectedTable.getRemarks()+"实体类*/");
//        topLevelClass.addJavaDocLine("@Mapper");
//        topLevelClass.addJavaDocLine("@Repository");
    }
    private void addFieldJavaDoc(Field field, String remarks) {
        //文档注释开始
        field.addJavaDocLine("/**");
        //获取数据库字段的备注信息
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for(String remarkLine:remarkLines){
            field.addJavaDocLine(" * "+remarkLine);
        }
//        addJavadocTag(field, false); //这个会添加一行 @mbg.generated的注释，完全没必要
        field.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            method.addJavaDocLine("/**设置" + introspectedColumn.getRemarks()+"*/");
//            method.addJavaDocLine("*@Param ");
//            method.addJavaDocLine("*/");
        }
    }

    /**将java.lang.String之类的名称简化为String,Tnteger改为int等*/
    private String trimJavaType(String javaFullType){
        //先去掉名称前缀
        int last=javaFullType.lastIndexOf('.');
        if(last!=-1){
            String simpleType=javaFullType.substring(last);
            if("Integer".equals(simpleType)){
                return "int";
            }else if("Long".equals(simpleType)){
                return "long";
            }else if("Double".equals(simpleType)){
                return "double";
            }else if("Float".equals(simpleType)){
                return "float";
            }else if("Char".equals(simpleType)){
                return "char";
            }else if("Short".equals(simpleType)){
                return "short";
            }else if("Byte".equals(simpleType)){
                return "byte";
            }
            return simpleType;
        }else {
            return javaFullType;
        }
    }
}
