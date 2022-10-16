# kotlin-mybatis

個人勉強用リポジトリ

教材出典：[Kotlin サーバーサイドプログラミング実践開発](https://gihyo.jp/book/2021/978-4-297-11859-4) ch5

## DB セットアップ
前提：Docker Desktop 導入済み

コンテナ作成 + 起動
```bash
docker container run --rm -d -e MYSQL_ROOT_PASSWORD=mysql -p 3306:3306 --name mysql mysql
```

コンテナに入る
```bash
docker exec -it mysql bash
```

ログイン
```bash
mysql -h 127.0.0.1 --port 3306 -uroot -pmysql
```

DB 作成
```sql
create database example;
```

使用する DB 指定
```sql
use example
```

テーブル作成
```sql
CREATE TABLE user (
  id int(10) NOT NULL,
  name varchar(16) NOT NULL,
  age int(10) NOT NULL,
  profile varchar(64) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

サンプルデータ作成
```sql
insert into user values
  (100, "Ichiro", 30, "Hello"),
  (101, "Jiro", 25, "Hello"),
  (102, "Saburo", 20, "Hello");
```
