import database.*
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder

fun main() {
    selectFunc()
}

fun selectFunc() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val user = mapper.selectByPrimaryKey(100)
        println(user)
    }

    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val userList = mapper.select {
            where { UserDynamicSqlSupport.name isEqualTo "Jiro" }
        }
        println(userList)
    }

    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val userList = mapper.select {
            where { UserDynamicSqlSupport.age isGreaterThanOrEqualTo 25 }
        }
        println(userList)
    }

    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.count { allRows() }
        println(count)
    }
}

fun insertSingle() {
    val user = User(103, "Shiro", 18, "Hello")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.insert(user)
        session.commit()
        println("${count}行のレコードを挿入しました")
    }
}

fun insertMultiple() {
    val userList = listOf(User(104, "Goro", 15, "Hello"), User(105, "Rokuro", 13, "Hello"))
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.insertMultiple(userList)
        session.commit()
        println("${count}行のレコードを挿入しました")
    }
}

fun updateByPrimaryKeySelectiveFunc() {
    val user = User(id = 105, profile = "Bye")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.updateByPrimaryKeySelective(user)
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

fun updateNotRecord() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.update {
            set(UserDynamicSqlSupport.profile).equalTo("Hey")
            where { UserDynamicSqlSupport.id isEqualTo 104 }
        }
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

fun updateRecord() {
    val user = User(profile = "Good Morning")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.update {
            updateSelectiveColumns(user)
            where { UserDynamicSqlSupport.name isEqualTo "Shiro" }
        }
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

fun createSessionFactory(): SqlSessionFactory {
    val resource = "mybatis-config.xml"
    val inputStream = Resources.getResourceAsStream(resource)
    return SqlSessionFactoryBuilder().build(inputStream)
}
