package storage.kundera

import java.util.UUID
import javax.persistence.{Column, Entity, Id, Table}

/**
 * CREATE TABLE users ( id text, name text, surname text, age int, primary key(id) ) WITH COMPACT STORAGE;
 */
@Entity
@Table (name = "users", schema = "kunderaexamples@cassandra_pu")
class UserKundera {

  @Id
  private var id: UUID = null

  @Column
  private var name: String = null

  @Column
  private var surname: String = null

  @Column
  private var age: Int = 0

  def getId: UUID = id
  def setId(_id: UUID) = id = _id
  def getName: String = name
  def setName(name: String) = this.name = name
  def getSurname: String = surname
  def setSurname(surname: String) = this.surname = surname
  def getAge: Int = age
  def setAge(age: Int) = this.age = age
}