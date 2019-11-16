package com.edison.springbootdemo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EmInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andEmIdIsNull() {
            addCriterion("em_id is null");
            return (Criteria) this;
        }

        public Criteria andEmIdIsNotNull() {
            addCriterion("em_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmIdEqualTo(Integer value) {
            addCriterion("em_id =", value, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdNotEqualTo(Integer value) {
            addCriterion("em_id <>", value, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdGreaterThan(Integer value) {
            addCriterion("em_id >", value, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("em_id >=", value, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdLessThan(Integer value) {
            addCriterion("em_id <", value, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdLessThanOrEqualTo(Integer value) {
            addCriterion("em_id <=", value, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdIn(List<Integer> values) {
            addCriterion("em_id in", values, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdNotIn(List<Integer> values) {
            addCriterion("em_id not in", values, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdBetween(Integer value1, Integer value2) {
            addCriterion("em_id between", value1, value2, "emId");
            return (Criteria) this;
        }

        public Criteria andEmIdNotBetween(Integer value1, Integer value2) {
            addCriterion("em_id not between", value1, value2, "emId");
            return (Criteria) this;
        }

        public Criteria andEmNameIsNull() {
            addCriterion("em_name is null");
            return (Criteria) this;
        }

        public Criteria andEmNameIsNotNull() {
            addCriterion("em_name is not null");
            return (Criteria) this;
        }

        public Criteria andEmNameEqualTo(String value) {
            addCriterion("em_name =", value, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameNotEqualTo(String value) {
            addCriterion("em_name <>", value, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameGreaterThan(String value) {
            addCriterion("em_name >", value, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameGreaterThanOrEqualTo(String value) {
            addCriterion("em_name >=", value, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameLessThan(String value) {
            addCriterion("em_name <", value, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameLessThanOrEqualTo(String value) {
            addCriterion("em_name <=", value, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameLike(String value) {
            addCriterion("em_name like", value, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameNotLike(String value) {
            addCriterion("em_name not like", value, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameIn(List<String> values) {
            addCriterion("em_name in", values, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameNotIn(List<String> values) {
            addCriterion("em_name not in", values, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameBetween(String value1, String value2) {
            addCriterion("em_name between", value1, value2, "emName");
            return (Criteria) this;
        }

        public Criteria andEmNameNotBetween(String value1, String value2) {
            addCriterion("em_name not between", value1, value2, "emName");
            return (Criteria) this;
        }

        public Criteria andEmSexIsNull() {
            addCriterion("em_sex is null");
            return (Criteria) this;
        }

        public Criteria andEmSexIsNotNull() {
            addCriterion("em_sex is not null");
            return (Criteria) this;
        }

        public Criteria andEmSexEqualTo(String value) {
            addCriterion("em_sex =", value, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexNotEqualTo(String value) {
            addCriterion("em_sex <>", value, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexGreaterThan(String value) {
            addCriterion("em_sex >", value, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexGreaterThanOrEqualTo(String value) {
            addCriterion("em_sex >=", value, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexLessThan(String value) {
            addCriterion("em_sex <", value, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexLessThanOrEqualTo(String value) {
            addCriterion("em_sex <=", value, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexLike(String value) {
            addCriterion("em_sex like", value, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexNotLike(String value) {
            addCriterion("em_sex not like", value, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexIn(List<String> values) {
            addCriterion("em_sex in", values, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexNotIn(List<String> values) {
            addCriterion("em_sex not in", values, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexBetween(String value1, String value2) {
            addCriterion("em_sex between", value1, value2, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmSexNotBetween(String value1, String value2) {
            addCriterion("em_sex not between", value1, value2, "emSex");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayIsNull() {
            addCriterion("em_birthday is null");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayIsNotNull() {
            addCriterion("em_birthday is not null");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("em_birthday =", value, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("em_birthday <>", value, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("em_birthday >", value, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("em_birthday >=", value, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayLessThan(Date value) {
            addCriterionForJDBCDate("em_birthday <", value, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("em_birthday <=", value, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("em_birthday in", values, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("em_birthday not in", values, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("em_birthday between", value1, value2, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmBirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("em_birthday not between", value1, value2, "emBirthday");
            return (Criteria) this;
        }

        public Criteria andEmHiredateIsNull() {
            addCriterion("em_hiredate is null");
            return (Criteria) this;
        }

        public Criteria andEmHiredateIsNotNull() {
            addCriterion("em_hiredate is not null");
            return (Criteria) this;
        }

        public Criteria andEmHiredateEqualTo(Date value) {
            addCriterionForJDBCDate("em_hiredate =", value, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateNotEqualTo(Date value) {
            addCriterionForJDBCDate("em_hiredate <>", value, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateGreaterThan(Date value) {
            addCriterionForJDBCDate("em_hiredate >", value, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("em_hiredate >=", value, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateLessThan(Date value) {
            addCriterionForJDBCDate("em_hiredate <", value, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("em_hiredate <=", value, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateIn(List<Date> values) {
            addCriterionForJDBCDate("em_hiredate in", values, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateNotIn(List<Date> values) {
            addCriterionForJDBCDate("em_hiredate not in", values, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("em_hiredate between", value1, value2, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmHiredateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("em_hiredate not between", value1, value2, "emHiredate");
            return (Criteria) this;
        }

        public Criteria andEmSalaryIsNull() {
            addCriterion("em_salary is null");
            return (Criteria) this;
        }

        public Criteria andEmSalaryIsNotNull() {
            addCriterion("em_salary is not null");
            return (Criteria) this;
        }

        public Criteria andEmSalaryEqualTo(Double value) {
            addCriterion("em_salary =", value, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryNotEqualTo(Double value) {
            addCriterion("em_salary <>", value, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryGreaterThan(Double value) {
            addCriterion("em_salary >", value, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryGreaterThanOrEqualTo(Double value) {
            addCriterion("em_salary >=", value, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryLessThan(Double value) {
            addCriterion("em_salary <", value, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryLessThanOrEqualTo(Double value) {
            addCriterion("em_salary <=", value, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryIn(List<Double> values) {
            addCriterion("em_salary in", values, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryNotIn(List<Double> values) {
            addCriterion("em_salary not in", values, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryBetween(Double value1, Double value2) {
            addCriterion("em_salary between", value1, value2, "emSalary");
            return (Criteria) this;
        }

        public Criteria andEmSalaryNotBetween(Double value1, Double value2) {
            addCriterion("em_salary not between", value1, value2, "emSalary");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}