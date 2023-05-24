package com.edison.springbootdemo;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LoadCSV {
    public static void main(String[] args) {
        // 读取配置文件获取数据库连接信息
        String dbUrl = "jdbc:mysql://192.168.92.107:3306/roadmonitor_passcar?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true";
        String username = "root";
        String password = "Zsxz12#$";

        // CSV文件路径和名称
        String csvFilePath = "d:\\tmp\\sfz.csv";

        try {
            // 连接数据库
            Connection connection = DriverManager.getConnection(dbUrl, username, password);

            // 创建PreparedStatement
            String insertQuery = "INSERT INTO base_toll_info (toll_station_id, toll_station_name, area_code, city, toll_segment_id, " +
                    "toll_segment_name, toll_station_hex_string, plaza_count, total_lanes, etc_lanes, mtc_lanes, hybrid_lanes, " +
                    "opening_time, toll_station_status, longitude, latitude, type, entity_type, collect_gantry_id, remark) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(insertQuery);

            // 读取CSV文件并写入数据库
            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            List<String[]> csvData = reader.readAll();

            // 跳过CSV文件的标题行
            for (int i = 1; i < csvData.size(); i++) {
                String[] row = csvData.get(i);

                // 设置PreparedStatement参数
                statement.setString(1, row[0]);
                statement.setString(2, row[1]);
                statement.setString(3, row[2]);
                statement.setString(4, row[3]);
                statement.setString(5, row[4]);
                statement.setString(6, row[5]);
                statement.setString(7, row[6]);
                statement.setString(8, row[7]);
                statement.setString(9, row[8]);
                statement.setString(10, row[9]);
                statement.setString(11, row[10]);
                statement.setString(12, row[11]);
                statement.setString(13, row[12]);
                statement.setString(14, row[13]);
                statement.setString(15, row[14]);
                statement.setString(16, row[15]);
                statement.setString(17, row[16]);
                statement.setString(18, row[17]);
                statement.setString(19, row[18]);
                statement.setString(20, row[19]);

                // 执行插入操作
                statement.executeUpdate();
            }

            // 关闭资源
            statement.close();
            connection.close();

            System.out.println("数据插入成功！");
        } catch (IOException | CsvException | SQLException e) {
            e.printStackTrace();
        }
    }
}
