package com.app.base.litepal;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.*;
import org.litepal.crud.*;
import org.litepal.crud.async.*;
import org.litepal.tablemanager.callback.*;

import java.util.Collection;
import java.util.List;

/**
 * The type Rx litepal.
 */
public class RxLitepal{

    /**
     * Initialize.
     *
     * @param context the context
     */
    public static void initialize(Context context){
        Operator.initialize(context);
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public static SQLiteDatabase getDatabase(){
        return Operator.getDatabase();
    }

    /**
     * Use.
     *
     * @param litePalDB the lite pal db
     */
    public static void use(LitePalDB litePalDB){
        Operator.use(litePalDB);
    }

    /**
     * Use default.
     */
    public static void useDefault(){
        Operator.useDefault();
    }

    /**
     * Delete database boolean.
     *
     * @param dbName the db name
     * @return the boolean
     */
    public static boolean deleteDatabase(String dbName){
        return Operator.deleteDatabase(dbName);
    }

    /**
     * Aes key.
     *
     * @param key the key
     */
    public static void aesKey(String key){
        Operator.aesKey(key);
    }

    /**
     * Select fluent query.
     *
     * @param columns the columns
     * @return the fluent query
     */
    public static RxFluentQuery select(String... columns){
        RxFluentQuery cQuery = new RxFluentQuery();
        cQuery.mColumns = columns;
        return cQuery;
    }

    /**
     * Where fluent query.
     *
     * @param conditions the conditions
     * @return the fluent query
     */
    public static RxFluentQuery where(String... conditions){
        RxFluentQuery cQuery = new RxFluentQuery();
        cQuery.mConditions = conditions;
        return cQuery;
    }

    /**
     * Order fluent query.
     *
     * @param column the column
     * @return the fluent query
     */
    public static RxFluentQuery order(String column){
        RxFluentQuery cQuery = new RxFluentQuery();
        cQuery.mOrderBy = column;
        return cQuery;
    }

    public static RxWhere where(String column, Condition condition){
        RxFluentQuery cQuery = new RxFluentQuery();
        return cQuery.where(column,condition);
    }

    public static RxWhere whereEqualTo(String column, Object value){
        RxFluentQuery cQuery = new RxFluentQuery();
        return cQuery.where(column,Condition.equalTo).addWhereValue(value);
    }

    public static RxWhere andWhere(String column, Condition condition){
        RxFluentQuery cQuery = new RxFluentQuery();
        return cQuery.andWhere(column,condition);
    }

    public static RxWhere andWhereEqualTo(String column, Object value){
        RxFluentQuery cQuery = new RxFluentQuery();
        return cQuery.andWhere(column,Condition.equalTo).addWhereValue(value);
    }

    public static RxWhere orWhere(String column, Condition condition){
        RxFluentQuery cQuery = new RxFluentQuery();
        return cQuery.orWhere(column,condition);
    }

    public static RxWhere orWhereEqualTo(String column, Object value){
        RxFluentQuery cQuery = new RxFluentQuery();
        return cQuery.orWhere(column,Condition.equalTo).addWhereValue(value);
    }

    public static RxFluentQuery order(String column, Order order){
        RxFluentQuery cQuery = new RxFluentQuery();
        cQuery.order(column,order);
        return cQuery;
    }

    /**
     * Limit fluent query.
     *
     * @param value the value
     * @return the fluent query
     */
    public static RxFluentQuery limit(int value){
        RxFluentQuery cQuery = new RxFluentQuery();
        cQuery.mLimit = String.valueOf(value);
        return cQuery;
    }

    /**
     * Offset fluent query.
     *
     * @param value the value
     * @return the fluent query
     */
    public static RxFluentQuery offset(int value){
        RxFluentQuery cQuery = new RxFluentQuery();
        cQuery.mOffset = String.valueOf(value);
        return cQuery;
    }

    /**
     * Count int.
     *
     * @param modelClass the model class
     * @return the int
     */
    public static int count(Class<?> modelClass){
        return Operator.count(modelClass);
    }

    /**
     * Count async count executor.
     *
     * @param modelClass the model class
     * @return the count executor
     */
    public static CountExecutor countAsync(final Class<?> modelClass){
        return Operator.countAsync(modelClass);
    }

    /**
     * Count int.
     *
     * @param tableName the table name
     * @return the int
     */
    public static int count(String tableName){
        return Operator.count(tableName);
    }

    /**
     * Count async count executor.
     *
     * @param tableName the table name
     * @return the count executor
     */
    public static CountExecutor countAsync(final String tableName){
        return Operator.countAsync(tableName);
    }

    /**
     * Average double.
     *
     * @param modelClass the model class
     * @param column     the column
     * @return the double
     */
    public static double average(Class<?> modelClass, String column){
        return Operator.average(modelClass,column);
    }

    /**
     * Average async average executor.
     *
     * @param modelClass the model class
     * @param column     the column
     * @return the average executor
     */
    public static AverageExecutor averageAsync(final Class<?> modelClass, final String column){
        return Operator.averageAsync(modelClass,column);
    }

    /**
     * Average double.
     *
     * @param tableName the table name
     * @param column    the column
     * @return the double
     */
    public static double average(String tableName, String column){
        return Operator.average(tableName,column);
    }

    /**
     * Average async average executor.
     *
     * @param tableName the table name
     * @param column    the column
     * @return the average executor
     */
    public static AverageExecutor averageAsync(final String tableName, final String column){
        return Operator.averageAsync(tableName,column);
    }

    /**
     * Max t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param columnName the column name
     * @param columnType the column type
     * @return the t
     */
    public static <T> T max(Class<?> modelClass, String columnName, Class<T> columnType){
        return Operator.max(modelClass,columnName,columnType);
    }

    /**
     * Max async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param columnName the column name
     * @param columnType the column type
     * @return the find executor
     */
    public static <T> FindExecutor<T> maxAsync(final Class<?> modelClass, final String columnName,
                                               final Class<T> columnType)
    {
        return Operator.maxAsync(modelClass,columnName,columnType);
    }

    /**
     * Max t.
     *
     * @param <T>        the type parameter
     * @param tableName  the table name
     * @param columnName the column name
     * @param columnType the column type
     * @return the t
     */
    public static <T> T max(String tableName, String columnName, Class<T> columnType){
        return Operator.max(tableName,columnName,columnType);
    }

    /**
     * Max async find executor.
     *
     * @param <T>        the type parameter
     * @param tableName  the table name
     * @param columnName the column name
     * @param columnType the column type
     * @return the find executor
     */
    public static <T> FindExecutor<T> maxAsync(final String tableName, final String columnName, final Class<T> columnType)
    {
        return Operator.maxAsync(tableName,columnName,columnType);
    }

    /**
     * Min t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param columnName the column name
     * @param columnType the column type
     * @return the t
     */
    public static <T> T min(Class<?> modelClass, String columnName, Class<T> columnType){
        return Operator.min(modelClass,columnName,columnType);
    }

    /**
     * Min async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param columnName the column name
     * @param columnType the column type
     * @return the find executor
     */
    public static <T> FindExecutor<T> minAsync(final Class<?> modelClass, final String columnName,
                                               final Class<T> columnType)
    {
        return Operator.minAsync(modelClass,columnName,columnType);
    }

    /**
     * Min t.
     *
     * @param <T>        the type parameter
     * @param tableName  the table name
     * @param columnName the column name
     * @param columnType the column type
     * @return the t
     */
    public static <T> T min(String tableName, String columnName, Class<T> columnType){
        return Operator.min(tableName,columnName,columnType);
    }

    /**
     * Min async find executor.
     *
     * @param <T>        the type parameter
     * @param tableName  the table name
     * @param columnName the column name
     * @param columnType the column type
     * @return the find executor
     */
    public static <T> FindExecutor<T> minAsync(final String tableName, final String columnName, final Class<T> columnType)
    {
        return Operator.minAsync(tableName,columnName,columnType);
    }

    /**
     * Sum t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param columnName the column name
     * @param columnType the column type
     * @return the t
     */
    public static <T> T sum(Class<?> modelClass, String columnName, Class<T> columnType){
        return Operator.sum(modelClass,columnName,columnType);
    }

    /**
     * Sum async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param columnName the column name
     * @param columnType the column type
     * @return the find executor
     */
    public static <T> FindExecutor<T> sumAsync(final Class<?> modelClass, final String columnName,
                                               final Class<T> columnType)
    {
        return Operator.sumAsync(modelClass,columnName,columnType);
    }

    /**
     * Sum t.
     *
     * @param <T>        the type parameter
     * @param tableName  the table name
     * @param columnName the column name
     * @param columnType the column type
     * @return the t
     */
    public static <T> T sum(String tableName, String columnName, Class<T> columnType){
        return Operator.sum(tableName,columnName,columnType);
    }

    /**
     * Sum async find executor.
     *
     * @param <T>        the type parameter
     * @param tableName  the table name
     * @param columnName the column name
     * @param columnType the column type
     * @return the find executor
     */
    public static <T> FindExecutor<T> sumAsync(final String tableName, final String columnName, final Class<T> columnType)
    {
        return Operator.sumAsync(tableName,columnName,columnType);
    }

    /**
     * Find t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param id         the id
     * @return the t
     */
    public static <T> T find(Class<T> modelClass, long id){
        return Operator.find(modelClass,id);
    }

    /**
     * Find async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param id         the id
     * @return the find executor
     */
    public static <T> FindExecutor<T> findAsync(Class<T> modelClass, long id){
        return Operator.findAsync(modelClass,id);
    }

    /**
     * Find t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param id         the id
     * @param isEager    the is eager
     * @return the t
     */
    public static <T> T find(Class<T> modelClass, long id, boolean isEager){
        return Operator.find(modelClass,id,isEager);
    }

    /**
     * Find async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param id         the id
     * @param isEager    the is eager
     * @return the find executor
     */
    public static <T> FindExecutor<T> findAsync(final Class<T> modelClass, final long id, final boolean isEager)
    {
        return Operator.findAsync(modelClass,id,isEager);
    }

    /**
     * Find first t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @return the t
     */
    public static <T> T findFirst(Class<T> modelClass){
        return Operator.findFirst(modelClass);
    }

    /**
     * Find first async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @return the find executor
     */
    public static <T> FindExecutor<T> findFirstAsync(Class<T> modelClass){
        return Operator.findFirstAsync(modelClass);
    }

    /**
     * Find first t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param isEager    the is eager
     * @return the t
     */
    public static <T> T findFirst(Class<T> modelClass, boolean isEager){
        return Operator.findFirst(modelClass,isEager);
    }

    /**
     * Find first async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param isEager    the is eager
     * @return the find executor
     */
    public static <T> FindExecutor<T> findFirstAsync(final Class<T> modelClass, final boolean isEager)
    {
        return Operator.findFirstAsync(modelClass,isEager);
    }

    /**
     * Find last t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @return the t
     */
    public static <T> T findLast(Class<T> modelClass){
        return Operator.findLast(modelClass);
    }

    /**
     * Find last async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @return the find executor
     */
    public static <T> FindExecutor<T> findLastAsync(Class<T> modelClass){
        return Operator.findLastAsync(modelClass);
    }

    /**
     * Find last t.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param isEager    the is eager
     * @return the t
     */
    public static <T> T findLast(Class<T> modelClass, boolean isEager){
        return Operator.findLast(modelClass,isEager);
    }

    /**
     * Find last async find executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param isEager    the is eager
     * @return the find executor
     */
    public static <T> FindExecutor<T> findLastAsync(final Class<T> modelClass, final boolean isEager)
    {
        return Operator.findLastAsync(modelClass,isEager);
    }

    /**
     * Find all list.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param ids        the ids
     * @return the list
     */
    public static <T> List<T> findAll(Class<T> modelClass, long... ids){
        return Operator.findAll(modelClass,ids);
    }

    /**
     * Find all async find multi executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param ids        the ids
     * @return the find multi executor
     */
    public static <T> FindMultiExecutor<T> findAllAsync(Class<T> modelClass, long... ids){
        return Operator.findAllAsync(modelClass,ids);
    }

    /**
     * Find all list.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param isEager    the is eager
     * @param ids        the ids
     * @return the list
     */
    public static <T> List<T> findAll(Class<T> modelClass, boolean isEager, long... ids){
        return Operator.findAll(modelClass,isEager,ids);
    }

    /**
     * Find all async find multi executor.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param isEager    the is eager
     * @param ids        the ids
     * @return the find multi executor
     */
    public static <T> FindMultiExecutor<T> findAllAsync(final Class<T> modelClass, final boolean isEager,
                                                        final long... ids)
    {
        return Operator.findAllAsync(modelClass,isEager,ids);
    }

    /**
     * Find by sql cursor.
     *
     * @param sql the sql
     * @return the cursor
     */
    public static Cursor findBySQL(String... sql){
        return Operator.findBySQL(sql);
    }

    /**
     * Delete int.
     *
     * @param modelClass the model class
     * @param id         the id
     * @return the int
     */
    public static int delete(Class<?> modelClass, long id){
        return Operator.delete(modelClass,id);
    }

    /**
     * Delete async update or delete executor.
     *
     * @param modelClass the model class
     * @param id         the id
     * @return the update or delete executor
     */
    public static UpdateOrDeleteExecutor deleteAsync(final Class<?> modelClass, final long id){
        return Operator.deleteAsync(modelClass,id);
    }

    /**
     * Delete all int.
     *
     * @param modelClass the model class
     * @param conditions the conditions
     * @return the int
     */
    public static int deleteAll(Class<?> modelClass, String... conditions){
        return Operator.deleteAll(modelClass,conditions);
    }

    /**
     * Delete all async update or delete executor.
     *
     * @param modelClass the model class
     * @param conditions the conditions
     * @return the update or delete executor
     */
    public static UpdateOrDeleteExecutor deleteAllAsync(final Class<?> modelClass, final String... conditions)
    {
        return Operator.deleteAllAsync(modelClass,conditions);
    }

    /**
     * Delete all int.
     *
     * @param tableName  the table name
     * @param conditions the conditions
     * @return the int
     */
    public static int deleteAll(String tableName, String... conditions){
        return Operator.deleteAll(tableName,conditions);
    }

    /**
     * Delete all async update or delete executor.
     *
     * @param tableName  the table name
     * @param conditions the conditions
     * @return the update or delete executor
     */
    public static UpdateOrDeleteExecutor deleteAllAsync(final String tableName, final String... conditions)
    {
        return Operator.deleteAllAsync(tableName,conditions);
    }

    /**
     * Update int.
     *
     * @param modelClass the model class
     * @param values     the values
     * @param id         the id
     * @return the int
     */
    public static int update(Class<?> modelClass, ContentValues values, long id){
        return Operator.update(modelClass,values,id);
    }

    /**
     * Update async update or delete executor.
     *
     * @param modelClass the model class
     * @param values     the values
     * @param id         the id
     * @return the update or delete executor
     */
    public static UpdateOrDeleteExecutor updateAsync(final Class<?> modelClass, final ContentValues values, final long id)
    {
        return Operator.updateAsync(modelClass,values,id);
    }

    /**
     * Update all int.
     *
     * @param modelClass the model class
     * @param values     the values
     * @param conditions the conditions
     * @return the int
     */
    public static int updateAll(Class<?> modelClass, ContentValues values, String... conditions){
        return Operator.updateAll(modelClass,values,conditions);
    }

    /**
     * Update all async update or delete executor.
     *
     * @param modelClass the model class
     * @param values     the values
     * @param conditions the conditions
     * @return the update or delete executor
     */
    public static UpdateOrDeleteExecutor updateAllAsync(Class<?> modelClass, ContentValues values, String... conditions)
    {
        return Operator.updateAllAsync(modelClass,values,conditions);
    }

    /**
     * Update all int.
     *
     * @param tableName  the table name
     * @param values     the values
     * @param conditions the conditions
     * @return the int
     */
    public static int updateAll(String tableName, ContentValues values, String... conditions){
        return Operator.updateAll(tableName,values,conditions);
    }

    /**
     * Update all async update or delete executor.
     *
     * @param tableName  the table name
     * @param values     the values
     * @param conditions the conditions
     * @return the update or delete executor
     */
    public static UpdateOrDeleteExecutor updateAllAsync(final String tableName, final ContentValues values,
                                                        final String... conditions)
    {
        return Operator.updateAllAsync(tableName,values,conditions);
    }

    /**
     * Save all.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     */
    public static <T extends LitePalSupport> void saveAll(Collection<T> collection){
        Operator.saveAll(collection);
    }

    /**
     * Save all async save executor.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the save executor
     */
    public static <T extends LitePalSupport> SaveExecutor saveAllAsync(final Collection<T> collection)
    {
        return Operator.saveAllAsync(collection);
    }

    /**
     * Mark as deleted.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     */
    public static <T extends LitePalSupport> void markAsDeleted(Collection<T> collection){
        Operator.markAsDeleted(collection);
    }

    /**
     * Is exist boolean.
     *
     * @param <T>        the type parameter
     * @param modelClass the model class
     * @param conditions the conditions
     * @return the boolean
     */
    public static <T> boolean isExist(Class<T> modelClass, String... conditions){
        return Operator.isExist(modelClass,conditions);
    }

    /**
     * Register database listener.
     *
     * @param listener the listener
     */
    public static void registerDatabaseListener(DatabaseListener listener){
        Operator.registerDatabaseListener(listener);
    }
}
