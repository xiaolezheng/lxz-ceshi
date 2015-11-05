package com.lxz.pg;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.lxz.util.JsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonDocument;
import org.bson.Document;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StopWatch;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by xiaolezheng on 15/10/16.
 */
@Slf4j
public class PgTest {
    static String selectSql = "SELECT id, class, \"dataId\", \"tenantId\", \"formId\", \"userId\", \"updateTime\", \n" +
            "       status, fields\n" +
            "  FROM \"xFormData\" limit 100000";

    static String insertSql = "INSERT INTO \"xFormData\"(\n" +
            "            class, \"dataId\", \"tenantId\", \"formId\", \"userId\", \"updateTime\", \n" +
            "            status, fields)\n" +
            "    VALUES ( ?, ?, ?, ?, ?, ?, \n" +
            "            ?, ?::jsonb);\n";

    static Random RANDOM = new Random();
    static int SEED = 20;

    public static void main(String[] args) {
        //makeDataInsertPg();

        makeDataInsertMongo();

        //selectDataFromPg();

        //selectDataFromMongo();

        /*for (int i = 0; i < 20; i++) {
            int rand = rand();
            log.info(createFormId(rand));
            log.info(createTenantId(rand));
        }*/

    }

    public static void selectDataFromMongo(){
        try{
            MongoClient mongoClient = new MongoClient( "192.168.1.117" , 27017 );
            MongoDatabase database = mongoClient.getDatabase("xForm");

            MongoCollection<Document> collection = database.getCollection("xFormData");

            StopWatch watch = new StopWatch();
            watch.start();

            List<Document> documentList = Lists.newArrayList();


            MongoCursor<Document> cursor = collection.find(Filters.eq("formId", "fm_0a5ea0c3cfaa33d5093d74987b3ac35_14")).iterator();
            while (cursor.hasNext()){
                Document document = cursor.next();

                documentList.add(document);
            }


            /*collection.find(Filters.eq("formId","fm_0a5ea0c3cfaa33d5093d74987b3ac35_14")).forEach(
                    new Consumer<Document>() {
                        @Override
                        public void accept(Document document) {
                            documentList.add(document);
                        }
                    }
            );*/

            log.info("count: {}", documentList.size());


            mongoClient.close();

            watch.stop();

            log.info(watch.prettyPrint());


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void makeDataInsertMongo(){
        try{
            MongoClient mongoClient = new MongoClient( "192.168.1.117" , 27017 );
            MongoDatabase database = mongoClient.getDatabase("xForm");

            MongoCollection<Document> collection = database.getCollection("xFormData");

            StopWatch watch = new StopWatch();
            watch.start();

            int index = 0;
            List<Document> documentList = Lists.newArrayListWithExpectedSize(100);

            for (int i = 0; i < 5000; i++) {
                FormData formData = createFormData();
                Document document = Document.parse(JsonUtil.encode(formData));
                documentList.add(document);
                index++;

                if(index == 1000){
                    collection.insertMany(documentList);
                    documentList.clear();
                    index = 0;
                }

            }

            mongoClient.close();

            watch.stop();

            log.info(watch.prettyPrint());


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void selectDataFromPg(){
        try{
            //TimeUnit.SECONDS.sleep(25);

            Class.forName("org.postgresql.Driver").newInstance();
            String url = "jdbc:postgresql://localhost:5432/xformdb";


            Connection con = DriverManager.getConnection(url, "xform", "xform");


            StopWatch watch = new StopWatch();
            watch.start();

            PreparedStatement preparedStatement = con.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();


            List<FormData> formDataList = Lists.newLinkedList();

            while (resultSet.next()){
                FormData formData = FormData.builder();

                String id = resultSet.getString("id");
                String cls = resultSet.getString("class");
                String dataId = resultSet.getString("dataId");
                String tenantId = resultSet.getString("tenantId");
                String formId = resultSet.getString("formId");
                long userId = resultSet.getLong("userId");
                Date updateTime = resultSet.getDate("updateTime");
                int status = resultSet.getInt("status");

                Map<String,Object> fields = JsonUtil.decode(resultSet.getString("fields"), new TypeReference<Map<String, Object>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });

                formData.setId(id).setDataId(dataId).setFormId(formId).setTenantId(tenantId);
                formData.setUserId(userId).setUpdateTime(updateTime).setStatus(status).setFields(fields);

                formDataList.add(formData);


                log.info("record: {}", fields);
            }


            preparedStatement.close();
            con.close();

            watch.stop();

            log.info("count: {}", formDataList.size());
            log.info(watch.prettyPrint());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void makeDataInsertPg() {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            String url = "jdbc:postgresql://localhost:5432/xformdb";


            Connection con = DriverManager.getConnection(url, "xform", "xform");


            StopWatch watch = new StopWatch();
            watch.start();

            PreparedStatement preparedStatement = con.prepareStatement(insertSql);

            for (int i = 0; i < 500000; i++) {
                int index = 1;
                FormData formData = createFormData();
                preparedStatement.setString(index++, "com.lxz.pg.FormData");
                preparedStatement.setString(index++, formData.getDataId());
                preparedStatement.setString(index++, formData.getTenantId());
                preparedStatement.setString(index++, formData.getFormId());
                preparedStatement.setLong(index++, formData.getUserId());
                preparedStatement.setDate(index++, formData.getUpdateTime());
                preparedStatement.setInt(index++, formData.getStatus());
                preparedStatement.setString(index++, JsonUtil.encode(formData.getFields()));

                preparedStatement.execute();
            }

            preparedStatement.close();
            con.close();

            watch.stop();

            log.info(watch.prettyPrint());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FormData createFormData() {
        FormData formData = FormData.builder();
        int rand = rand();

        formData.setTenantId(createTenantId(rand));
        formData.setFormId(createFormId(rand));
        formData.setStatus(0);
        formData.setUpdateTime(new Date(System.currentTimeMillis()));
        formData.setUserId(1L);
        formData.setFields(createFields(rand));

        return formData;
    }

    public static Map<String, Object> createFields(int rand) {
        String field1 = "fm_f_dbe58ab52d57b2fc7e7a4361de9479f" + rand++;
        String field2 = "fm_f_dbe58ab52d57b2fc7e7a4361de9479f" + rand++;
        String field3 = "fm_f_dbe58ab52d57b2fc7e7a4361de9479f" + rand++;
        String field4 = "fm_f_dbe58ab52d57b2fc7e7a4361de9479f" + rand++;

        String value1 = "apple" + RANDOM.nextInt(100);
        int value2 = RANDOM.nextInt(1000);
        int value3 = RANDOM.nextInt(1000);
        String value4 = "A123123132AASDASD" + RANDOM.nextInt(100);

        return ImmutableMap.of(field1, value1, field2, value2, field3, value3, field4, value4);
    }


    public static String createTenantId(int rand) {
        return Joiner.on('_').join("bdp", rand);
    }

    public static String createFormId(int rand) {
        return Joiner.on('_').join("fm_0a5ea0c3cfaa33d5093d74987b3ac35", rand);
    }

    private static int rand() {
        return RANDOM.nextInt(SEED);
    }
}
