package org.oclc.hadoop.metrics;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by hughj on 2019-05-07
 */
public class HbaseTest {
   @Test
   public void connecttest() {
      try (Connection con = ConnectionFactory.createConnection();
           Admin admin = con.getAdmin();
      ) {
         assertNotNull(con);
         // there should a table called 'test' for this to succeed!
         assertTrue(admin.tableExists(TableName.valueOf("test")));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void putTest() {
      try (Connection con = ConnectionFactory.createConnection();
           Admin admin = con.getAdmin();
      ) {
         byte[] FAMILY = Bytes.toBytes("cf");
         // there should a table called 'test' for this to succeed!
         Table table = con.getTable(TableName.valueOf("test"));
         Put row = new Put(Bytes.toBytes("key1"));
         row.addColumn(FAMILY, Bytes.toBytes("a"), Bytes.toBytes("alpha"));
         row.addColumn(FAMILY, Bytes.toBytes("b"), Bytes.toBytes("beta"));
         row.addColumn(FAMILY, Bytes.toBytes("c"), Bytes.toBytes("charlie"));
         table.put(row);
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   @Test
   public void getTest() {
      try (Connection con = ConnectionFactory.createConnection();
           Admin admin = con.getAdmin();
      ) {
         byte[] FAMILY = Bytes.toBytes("cf");
         // there should a table called 'test' for this to succeed!
         Table table = con.getTable(TableName.valueOf("test"));
         Get get = new Get(Bytes.toBytes("key1"));
         Result result = table.get(get);
         assertFalse(result.isEmpty());
         assertEquals(Bytes.toString(result.getValue(FAMILY, Bytes.toBytes("b"))), "beta");
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
}
