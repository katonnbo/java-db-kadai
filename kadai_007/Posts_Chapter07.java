package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
    public static void main(String[] args) {
        Connection con = null;
        Statement statement = null;

        try {
            // データベースに接続する
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "Krar0217"
            );

            System.out.println("データベース接続成功:" + con);

            // SQLクエリを準備する
            statement = con.createStatement();
            System.out.println("レコード追加を実行します");

            // 既存のデータを削除
            String truncatesql = "TRUNCATE TABLE posts;";
            statement.executeUpdate(truncatesql);

            // 投稿データを追加する
            String insertsql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES"
                    + "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13),"
                    + "(1002, '2023-02-08', 'お疲れ様です！', 12),"
                    + "(1003, '2023-02-09', '今日も頑張ります！', 18),"
                    + "(1001, '2023-02-09', '無理は禁物ですよ！', 17),"
                    + "(1002, '2023-02-10', '明日から連休ですね！', 20);";

            int rowCnt = statement.executeUpdate(insertsql);
            System.out.println(rowCnt + "件のレコードが追加されました");

            // 投稿データを検索する
            Statement selectstatement = con.createStatement();
            String selectsql = "SELECT * FROM posts WHERE user_id = 1002;";
            System.out.println("ユーザーIDが1002のレコードを検索しました");
            ResultSet result = selectstatement.executeQuery(selectsql);

            while(result.next()) {
                String posted_at = result.getString("posted_at");
                String post_content = result.getString("post_content");
                int likes = result.getInt("likes");
                System.out.println(result.getRow() + "件目:投稿日時=" + posted_at + "／投稿内容=" + post_content + "／いいね数=" + likes);
            }

        } catch(SQLException e) {
            System.out.println("エラー発生:" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if(statement != null) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if(con != null) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
    }
}

