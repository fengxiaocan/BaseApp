package com.app.base.litepal;

import java.util.ArrayList;
import java.util.List;

public class RxWhere{
    private RxFluentQuery query;
    private StringBuilder mWhere;
    private List<String> mWhereValue;

    public RxWhere(RxFluentQuery values){
        this.query = values;
        if(values.mConditions != null && values.mConditions.length > 0){
            mWhere = new StringBuilder(values.mConditions[0]);
            for(int i = 1;i < values.mConditions.length;i++){
                mWhereValue.add(values.mConditions[i]);
            }
        } else{
            mWhere = new StringBuilder();
            mWhereValue = new ArrayList<>();
        }
    }

    public RxWhere andWhere(String column, Condition condition){
        if(mWhere.length() > 0){
            mWhere.append(" and ");
        }
        mWhere.append(column);
        mWhere.append(condition.getValue());
        return this;
    }

    public RxWhere orWhere(String column, Condition condition){
        if(mWhere.length() > 0){
            mWhere.append(" or ");
        }
        mWhere.append(column);
        mWhere.append(condition.getValue());
        return this;
    }

    public RxWhere addWhereValue(Object value){
        mWhereValue.add(String.valueOf(value));
        return this;
    }

    public RxWhere addWhereValue(String value){
        mWhereValue.add(value);
        return this;
    }

    public RxFluentQuery asWhere(){
        query.mConditions = new String[mWhereValue.size() + 1];
        query.mConditions[0] = mWhere.toString();
        for(int i = 0;i < mWhereValue.size();i++){
            query.mConditions[i + 1] = mWhereValue.get(i);
        }
        return query;
    }
}
