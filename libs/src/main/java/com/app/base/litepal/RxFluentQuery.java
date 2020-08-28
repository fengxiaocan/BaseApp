package com.app.base.litepal;

import org.litepal.*;

public class RxFluentQuery extends FluentQuery{
    public RxFluentQuery(){
        super();
    }

    public RxFluentQuery(FluentQuery fluentQuery){
        super();
        this.mColumns = fluentQuery.mColumns;
        this.mConditions = fluentQuery.mConditions;
        this.mOrderBy = fluentQuery.mOrderBy;
        this.mLimit = fluentQuery.mLimit;
        this.mOffset = fluentQuery.mOffset;
    }

    @Override
    public RxFluentQuery select(String... columns){
        FluentQuery select = super.select(columns);
        return this;
    }

    @Override
    public RxFluentQuery where(String... conditions){
        super.where(conditions);
        return this;
    }


    public RxWhere andWhere(String column, Condition condition){
        RxWhere rxWhere = new RxWhere(this);
        rxWhere.andWhere(column,condition);
        return rxWhere;
    }

    public RxWhere where(String column, Condition condition){
        RxWhere rxWhere = new RxWhere(this);
        rxWhere.andWhere(column,condition);
        return rxWhere;
    }


    public RxWhere orWhere(String column, Condition condition){
        RxWhere rxWhere = new RxWhere(this);
        rxWhere.orWhere(column,condition);
        return rxWhere;
    }

    public RxFluentQuery order(String column, Order order){
        super.order(column + order.getValue());
        return this;
    }

    @Override
    public RxFluentQuery order(String column){
        super.order(column);
        return this;
    }

    @Override
    public RxFluentQuery limit(int value){
        super.limit(value);
        return this;
    }

    @Override
    public RxFluentQuery offset(int value){
        super.offset(value);
        return this;
    }

    /**
     * 删除所有条件相关的
     *
     * @param modelClass
     * @param <T>
     */
    public <T> void delectAll(Class<T> modelClass){
        RxLitepal.deleteAll(modelClass,mConditions);
    }

    /**
     * 删除所有条件相关的
     *
     * @param modelClass
     * @param <T>
     */
    public <T> void deleteAllAsync(Class<T> modelClass){
        RxLitepal.deleteAllAsync(modelClass,mConditions);
    }
}
