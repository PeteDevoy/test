package com.cn.pflow.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PflowcountSelect {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PflowcountSelect() {
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

        protected void addCriterionForJDBCTime(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                timeList.add(new java.sql.Time(iter.next().getTime()));
            }
            addCriterion(condition, timeList, property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);
        }

        public Criteria andMacIsNull() {
            addCriterion("mac is null");
            return (Criteria) this;
        }

        public Criteria andMacIsNotNull() {
            addCriterion("mac is not null");
            return (Criteria) this;
        }

        public Criteria andMacEqualTo(String value) {
            addCriterion("mac =", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotEqualTo(String value) {
            addCriterion("mac <>", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacGreaterThan(String value) {
            addCriterion("mac >", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacGreaterThanOrEqualTo(String value) {
            addCriterion("mac >=", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLessThan(String value) {
            addCriterion("mac <", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLessThanOrEqualTo(String value) {
            addCriterion("mac <=", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLike(String value) {
            addCriterion("mac like", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotLike(String value) {
            addCriterion("mac not like", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacIn(List<String> values) {
            addCriterion("mac in", values, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotIn(List<String> values) {
            addCriterion("mac not in", values, "mac");
            return (Criteria) this;
        }

        public Criteria andMacBetween(String value1, String value2) {
            addCriterion("mac between", value1, value2, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotBetween(String value1, String value2) {
            addCriterion("mac not between", value1, value2, "mac");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNull() {
            addCriterion("startdate is null");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNotNull() {
            addCriterion("startdate is not null");
            return (Criteria) this;
        }

        public Criteria andStartdateEqualTo(Date value) {
            addCriterionForJDBCDate("startdate =", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("startdate <>", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThan(Date value) {
            addCriterionForJDBCDate("startdate >", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("startdate >=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThan(Date value) {
            addCriterionForJDBCDate("startdate <", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("startdate <=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateIn(List<Date> values) {
            addCriterionForJDBCDate("startdate in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("startdate not in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("startdate between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("startdate not between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andStarttimeIsNull() {
            addCriterion("starttime is null");
            return (Criteria) this;
        }

        public Criteria andStarttimeIsNotNull() {
            addCriterion("starttime is not null");
            return (Criteria) this;
        }

        public Criteria andStarttimeEqualTo(Date value) {
            addCriterionForJDBCTime("starttime =", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotEqualTo(Date value) {
            addCriterionForJDBCTime("starttime <>", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeGreaterThan(Date value) {
            addCriterionForJDBCTime("starttime >", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("starttime >=", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeLessThan(Date value) {
            addCriterionForJDBCTime("starttime <", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("starttime <=", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeIn(List<Date> values) {
            addCriterionForJDBCTime("starttime in", values, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotIn(List<Date> values) {
            addCriterionForJDBCTime("starttime not in", values, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("starttime between", value1, value2, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("starttime not between", value1, value2, "starttime");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNull() {
            addCriterion("enddate is null");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNotNull() {
            addCriterion("enddate is not null");
            return (Criteria) this;
        }

        public Criteria andEnddateEqualTo(Date value) {
            addCriterionForJDBCDate("enddate =", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotEqualTo(Date value) {
            addCriterionForJDBCDate("enddate <>", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThan(Date value) {
            addCriterionForJDBCDate("enddate >", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("enddate >=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThan(Date value) {
            addCriterionForJDBCDate("enddate <", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("enddate <=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateIn(List<Date> values) {
            addCriterionForJDBCDate("enddate in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotIn(List<Date> values) {
            addCriterionForJDBCDate("enddate not in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("enddate between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("enddate not between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNull() {
            addCriterion("endtime is null");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNotNull() {
            addCriterion("endtime is not null");
            return (Criteria) this;
        }

        public Criteria andEndtimeEqualTo(Date value) {
            addCriterionForJDBCTime("endtime =", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotEqualTo(Date value) {
            addCriterionForJDBCTime("endtime <>", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThan(Date value) {
            addCriterionForJDBCTime("endtime >", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("endtime >=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThan(Date value) {
            addCriterionForJDBCTime("endtime <", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("endtime <=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIn(List<Date> values) {
            addCriterionForJDBCTime("endtime in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotIn(List<Date> values) {
            addCriterionForJDBCTime("endtime not in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("endtime between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("endtime not between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andZoneidIsNull() {
            addCriterion("zoneid is null");
            return (Criteria) this;
        }

        public Criteria andZoneidIsNotNull() {
            addCriterion("zoneid is not null");
            return (Criteria) this;
        }

        public Criteria andZoneidEqualTo(Integer value) {
            addCriterion("zoneid =", value, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidNotEqualTo(Integer value) {
            addCriterion("zoneid <>", value, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidGreaterThan(Integer value) {
            addCriterion("zoneid >", value, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidGreaterThanOrEqualTo(Integer value) {
            addCriterion("zoneid >=", value, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidLessThan(Integer value) {
            addCriterion("zoneid <", value, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidLessThanOrEqualTo(Integer value) {
            addCriterion("zoneid <=", value, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidIn(List<Integer> values) {
            addCriterion("zoneid in", values, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidNotIn(List<Integer> values) {
            addCriterion("zoneid not in", values, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidBetween(Integer value1, Integer value2) {
            addCriterion("zoneid between", value1, value2, "zoneid");
            return (Criteria) this;
        }

        public Criteria andZoneidNotBetween(Integer value1, Integer value2) {
            addCriterion("zoneid not between", value1, value2, "zoneid");
            return (Criteria) this;
        }

        public Criteria andTotalentersIsNull() {
            addCriterion("totalenters is null");
            return (Criteria) this;
        }

        public Criteria andTotalentersIsNotNull() {
            addCriterion("totalenters is not null");
            return (Criteria) this;
        }

        public Criteria andTotalentersEqualTo(Long value) {
            addCriterion("totalenters =", value, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersNotEqualTo(Long value) {
            addCriterion("totalenters <>", value, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersGreaterThan(Long value) {
            addCriterion("totalenters >", value, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersGreaterThanOrEqualTo(Long value) {
            addCriterion("totalenters >=", value, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersLessThan(Long value) {
            addCriterion("totalenters <", value, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersLessThanOrEqualTo(Long value) {
            addCriterion("totalenters <=", value, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersIn(List<Long> values) {
            addCriterion("totalenters in", values, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersNotIn(List<Long> values) {
            addCriterion("totalenters not in", values, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersBetween(Long value1, Long value2) {
            addCriterion("totalenters between", value1, value2, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalentersNotBetween(Long value1, Long value2) {
            addCriterion("totalenters not between", value1, value2, "totalenters");
            return (Criteria) this;
        }

        public Criteria andTotalexitsIsNull() {
            addCriterion("totalexits is null");
            return (Criteria) this;
        }

        public Criteria andTotalexitsIsNotNull() {
            addCriterion("totalexits is not null");
            return (Criteria) this;
        }

        public Criteria andTotalexitsEqualTo(Long value) {
            addCriterion("totalexits =", value, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsNotEqualTo(Long value) {
            addCriterion("totalexits <>", value, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsGreaterThan(Long value) {
            addCriterion("totalexits >", value, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsGreaterThanOrEqualTo(Long value) {
            addCriterion("totalexits >=", value, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsLessThan(Long value) {
            addCriterion("totalexits <", value, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsLessThanOrEqualTo(Long value) {
            addCriterion("totalexits <=", value, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsIn(List<Long> values) {
            addCriterion("totalexits in", values, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsNotIn(List<Long> values) {
            addCriterion("totalexits not in", values, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsBetween(Long value1, Long value2) {
            addCriterion("totalexits between", value1, value2, "totalexits");
            return (Criteria) this;
        }

        public Criteria andTotalexitsNotBetween(Long value1, Long value2) {
            addCriterion("totalexits not between", value1, value2, "totalexits");
            return (Criteria) this;
        }

        public Criteria andEntersIsNull() {
            addCriterion("enters is null");
            return (Criteria) this;
        }

        public Criteria andEntersIsNotNull() {
            addCriterion("enters is not null");
            return (Criteria) this;
        }

        public Criteria andEntersEqualTo(Integer value) {
            addCriterion("enters =", value, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersNotEqualTo(Integer value) {
            addCriterion("enters <>", value, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersGreaterThan(Integer value) {
            addCriterion("enters >", value, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersGreaterThanOrEqualTo(Integer value) {
            addCriterion("enters >=", value, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersLessThan(Integer value) {
            addCriterion("enters <", value, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersLessThanOrEqualTo(Integer value) {
            addCriterion("enters <=", value, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersIn(List<Integer> values) {
            addCriterion("enters in", values, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersNotIn(List<Integer> values) {
            addCriterion("enters not in", values, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersBetween(Integer value1, Integer value2) {
            addCriterion("enters between", value1, value2, "enters");
            return (Criteria) this;
        }

        public Criteria andEntersNotBetween(Integer value1, Integer value2) {
            addCriterion("enters not between", value1, value2, "enters");
            return (Criteria) this;
        }

        public Criteria andExitsIsNull() {
            addCriterion("exits is null");
            return (Criteria) this;
        }

        public Criteria andExitsIsNotNull() {
            addCriterion("exits is not null");
            return (Criteria) this;
        }

        public Criteria andExitsEqualTo(Integer value) {
            addCriterion("exits =", value, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsNotEqualTo(Integer value) {
            addCriterion("exits <>", value, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsGreaterThan(Integer value) {
            addCriterion("exits >", value, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsGreaterThanOrEqualTo(Integer value) {
            addCriterion("exits >=", value, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsLessThan(Integer value) {
            addCriterion("exits <", value, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsLessThanOrEqualTo(Integer value) {
            addCriterion("exits <=", value, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsIn(List<Integer> values) {
            addCriterion("exits in", values, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsNotIn(List<Integer> values) {
            addCriterion("exits not in", values, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsBetween(Integer value1, Integer value2) {
            addCriterion("exits between", value1, value2, "exits");
            return (Criteria) this;
        }

        public Criteria andExitsNotBetween(Integer value1, Integer value2) {
            addCriterion("exits not between", value1, value2, "exits");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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