package com.developer.ayush.constants;

import com.developer.ayush.constants.TableList.TableWrapper;

public class TableList {
	public static class TableWrapper{
        private String tableName;
        public TableWrapper(String tableName)
        {
            this.tableName = tableName;
        }
        public String getTableName()
        {
            return tableName;
        }
	}
	
    public static final TableWrapper USER = new TableWrapper("USER");
   
}