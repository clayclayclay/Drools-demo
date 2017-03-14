# 1. User Guide
## 1.1 prerequisites:
* JDK 1.8  [click here to download](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Eclipse Neno version [click here to download](http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/neon/3.RC2/eclipse-jee-neon-3-RC2-win32-x86_64.zip&mirror_id=96)
* Maven [click here to download](http://maven.apache.org/download.cgi)
* Drools plugin [click here to download](http://www.drools.org/download/download.html)

## 1.2 the detail setup tutorial about Drools
1. download Drools and jBPM tools  [click here to download](http://www.drools.org/download/download.html)

![download page](http://upload-images.jianshu.io/upload_images/1688022-908940b49bedb892.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


2. after download done, unzip droolsjbpm-tools-distribution-6.5.0.Final.zip to eclipsem root directory
3. click Eclipse menu -> Windows -> Preference -> drools， if below picture occuring, follow the next
![install drools](http://upload-images.jianshu.io/upload_images/1688022-1f1c9937dbc622f4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4. click install Drools Runtimea and check it.

## 1.3 start a simple Drools project
1. build a new Drools  project
![Build a new drools project](http://upload-images.jianshu.io/upload_images/1688022-8573363abb4bebfd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2. chose the second option (this type contains some basic files including java and rule)
![select option](http://upload-images.jianshu.io/upload_images/1688022-9fe2babae08ae5aa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3. this is the structrue about the drools demo project
![project structure](http://upload-images.jianshu.io/upload_images/1688022-78752f2568ae33ca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4. this is a simple rule file
![rule file](http://upload-images.jianshu.io/upload_images/1688022-1084caba52651d84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
5. run and check the output
![output](http://upload-images.jianshu.io/upload_images/1688022-9776e721b29d5678.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



# 2. Drools basic syntax
## 2.1 Overview

**xxx.drl file structure:**

### package package-name

### imports

### globals

### functions

### declare

### queries

### rules

![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/package.png)

## 2.2 Specific

### 2.2.1 Package:

> A package is a collection of rules and other related constructs, such as imports and globals. The package members are typically related to each other - perhaps HR rules, for instance. A package represents a namespace, which ideally is kept unique for a given grouping of rules. The package name itself is the namespace, and is not related to files or folders in any way.

`package` 这个关键字用来声明一个rule文件的namespace，不需要与实际的物理路径对应。其声明位置需要置于最上方。

example:

```
package com.max.rule
```

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e8098) to find more details about **package**.



### 2.2.2 import:
![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/import.png)

> Import statements work like import statements in Java. You need to specify the fully qualified paths and type names for any objects you want to use in the rules. Drools automatically imports classes from the Java package of the same name, and also from the package java.lang.

`import` 关键字的用法和java一样。需要注意的是，如果在规则中用到了哪些Class，则需要用import语句声明。          

example:

```
import java.util.List;
import java.util.ArrayList;
import com.max.Message;
```

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e8121) to find more details about **import**.



### 2.2.3 global:

![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/global.png)


> With global you define global variables. They are used to make application objects available to the rules. Typically, they are used to provide data or services that the rules use, especially application services used in rule consequences, and to return data from the rules, like logs or values added in rule consequences, or for the rules to interact with the application, doing callbacks. Globals are not inserted into the Working Memory, and therefore a global should never be used to establish conditions in rules except when it has a constant immutable value. The engine cannot be notified about value changes of globals and does not track their changes. Incorrect use of globals in constraints may yield surprising results - surprising in a bad way.
> If multiple packages declare globals with the same identifier they must be of the same type and all of them will reference the same global value.

`global`关键字是数据在java code 和 rule 中传输的一座 "桥梁"。通过`global`可以在rule中调用global 对象的属性，方法等。一旦rule对这些global对象的属性修改过之后，同样可以在java code中获取这些改变。

1. Declare your global variable in your rules file and use it in rules. Example:


```
global java.util.List myGlobalList;

rule "Using a global"
when
    eval( true )
then
    myGlobalList.add( "Hello World" );
end
```

2.Set the global value on your working memory. It is a best practice to set all global values before asserting any fact to the working memory. Example:

```
List list = new ArrayList();
KieSession kieSession = kiebase.newKieSession();
kieSession.setGlobal( "myGlobalList", list );
```

> note: Globals are not designed to share data between rules and they should never be used for that purpose. Rules always reason and react to the working memory state, so if you want to pass data from rule to rule, assert the data as facts into the working memory.

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e8135) to find more details about **global**.



### 2.2.4 function:

#### 1.internal function (declare):

![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/function.png)

> Functions are a way to put semantic code in your rule source file, as opposed to in normal Java classes. They can't do anything more than what you can do with helper classes. (In fact, the compiler generates the helper class for you behind the scenes.) The main advantage of using functions in a rule is that you can keep the logic all in one place, and you can change the functions as needed (which can be a good or a bad thing). Functions are most useful for invoking actions on the consequence (then) part of a rule, especially if that particular action is used over and over again, perhaps with only differing parameters for each rule.

可以直接在rule中定义函数，称之为内部函数。定义函数的语法和java的几乎一致。

可以利用一些内部函数来替代掉一些频繁、不变的执行语句。

A typical function declaration looks like:

```
function String hello(String name) {
    return "Hello "+name+"!";
}
```

#### 2.external fuciton (import):

> Alternatively, you could use a static method in a helper class, e.g., Foo.hello(). Drools supports the use of function imports, so all you would need to do is:

可以在rule中导入外部函数，外部函数指的就是某个java类的静态函数。

`import function my.package.Foo.hello`

and call the method hello():

```
rule "using a static function"
when 
    eval( true )
then
    System.out.println( hello( "Bob" ) );
end
```

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e8178) to find more details about **function**.


### 2.2.5 declare:

#### 1.declare New Types:

##### 1.1 declare meta_data:

![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/meta_data.png)

> Metadata may be assigned to several different constructions in Drools: fact types, fact attributes and rules. Drools uses the at sign ('@') to introduce metadata, and it always uses the form:

直接定义元数据的表达式如下：

` @metadata_key( metadata_value ) `

example:

```
import java.util.Date

declare Person
    @author( Bob )
    @dateOfCreation( 01-Feb-2009 )

    name : String @key @maxLength( 30 )
    dateOfBirth : Date 
    address : Address
end
```

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e8397) to find more details about **meta_data**.


##### 1.2 declare type_declaration:

![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/type_declaration.png)


> To declare a new type, all you need to do is use the keyword declare, followed by the list of fields, and the keyword end. A new fact must have a list of fields, otherwise the engine will look for an existing fact class in the classpath and raise an error if not found.

可以直接在rule中声明新的数据类型。

example:

```
import java.util.Date

declare Person
    name : String
    dateOfBirth : Date
    address : Address
end
```

usage:

在java代码中，通过drools的api来获取在rule中声明的数据类型。

**in java code**

```
// get a reference to a knowledge base with a declared type:
KieBase kbase = ...

// get the declared FactType
FactType personType = kbase.getFactType( "org.drools.examples",
                                         "Person" );

// handle the type as necessary:
// create instances:
Object bob = personType.newInstance();

// set attributes values
personType.set( bob,
                "name",
                "Bob" );
personType.set( bob,
                "age",
                42 );

// insert fact into a session
KieSession ksession = ...
ksession.insert( bob );
ksession.fireAllRules();

// read attributes
String name = personType.get( bob, "name" );
int age = personType.get( bob, "age" );
```

**in xxx.drl file**

```
rule "Using a declared Type"
when 
    $p : Person( name == "Bob" )
then
    // Insert Mark, who is Bob's mate.
    Person mark = new Person();
    mark.setName("Mark");
    insert( mark );
end
```

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e8240) to find more details about **declare New Types**.

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e8212) to find more details about **Type Declaration**.




### 2.2.6 query:

![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/query.png)

> A query is a simple way to search the working memory for facts that match the stated conditions. Therefore, it contains only the structure of the LHS of a rule, so that you specify neither "when" nor "then". A query has an optional set of parameters, each of which can be optionally typed. If the type is not given, the type Object is assumed. The engine will attempt to coerce the values as needed. Query names are global to the KieBase; so do not add queries of the same name to different packages for the same RuleBase.

可以利用`query`语句，再根据一些限制条件来查询已经被insert到working memory中的数据对象。

example :

**in xxx.drl file**

```
query "people over the age of 30" 
    person : Person( age > 30 )
end
```

**in java code**

```
QueryResults results = ksession.getQueryResults( "people over the age of 30" );
System.out.println( "we have " + results.size() + " people over the age  of 30" );

System.out.println( "These people are are over 30:" );

for ( QueryResultsRow row : results ) {
    Person person = ( Person ) row.get( "person" );
    System.out.println( person.getName() + "\n" );
}
```

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#QuerySection) to find more details about **query**.




### 2.2.7 rules:

#### 1.rules section structure:

![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/rule.png)

> A rule specifies that when a particular set of conditions occur, specified in the Left Hand Side (LHS), then do what queryis specified as a list of actions in the Right Hand Side (RHS). A common question from users is "Why use when instead of if?" "When" was chosen over "if" because "if" is normally part of a procedural execution flow, where, at a specific point in time, a condition is to be checked. In contrast, "when" indicates that the condition evaluation is not tied to a specific evaluation sequence or point in time, but that it happens continually, at any time during the life time of the engine; whenever the condition is met, the actions are executed.

**the Rule Syntax Overview**

```
rule "<name>"
    <attribute>*
when
    <conditional element>*
then
    <action>*
end
```

rule example:

```
rule "Approve if not rejected"
  salience -100 
  agenda-group "approval"
    when
        not Rejection() 
        p : Policy(approved == false, policyState:status)
        exists Driver(age > 25)
        Process(status == policyState)
    then
        log("APPROVED: due to no objections."); 
        p.setApproved(true);
end
```

#### 2. Attributes:

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e9196) to find more about rule **attributes**.

#### 3. Left Hand Side (When) syntax:

##### 3.1 What is a pattern?

![image](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/images/LanguageReference/pattern.png)

> A pattern element is the most important Conditional Element. It can potentially match on each fact that is inserted in the working memory.A pattern contains of zero or more constraints and has an optional pattern binding. The railroad diagram below shows the syntax for this.

simple example:

```
rule "2 unconnected patterns"
when
    Pattern1()
    Pattern2()
then
    ... // actions
end

// The above rule is internally rewritten as:

rule "2 and connected patterns"
when
    Person(age > 10, name == "Max", gender == "male")
    Account(name == "admin", password == "123456")
then
    ... // actions
end
```

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e9456) to find more details about **LHS**.

#### 3. Right Hand Side (Then) syntax:

> The Right Hand Side (RHS) is a common name for the consequence or action part of the rule; this part should contain a list of actions to be executed. It is bad practice to use imperative or conditional code in the RHS of a rule; as a rule should be atomic in nature - "when this, then do this", not "when this, maybe do this". The RHS part of a rule should also be kept small, thus keeping it declarative and readable. If you find you need imperative and/or conditional code in the RHS, then maybe you should be breaking that rule down into multiple rules. The main purpose of the RHS is to insert, delete or modify working memory data. To assist with that there are a few convenience methods you can use to modify working memory; without having to first reference a working memory instance.

sample example:

```
rule "2 unconnected patterns"
when
    $person : Person( id == 1, name == "Max")
then
    $person.setGender("male");
    $person.setAge(22);
end
```

There are many uasges about some methods ： `insert`, `delete`, `update`.   [click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e11687) to find more details.

[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e11687) to find more details about **RHS**.


[click here](https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html_single/index.html#d0e9154) to find more details about **rule**.



# 3. Drools basic api

* KieServices
* KieFileSystem
* KieModule
* KieModuleModel
* KieBase
* KieBaseModel
* KieBuilder
* KieContainer
* KieSession

## 3.1 KieServices

Get a KieServices instance as the rule engine entrance.

```
KieServices ks = KieServices.Factory.get();
```

Calling method `getKieClasspathContainer` to load static classpath rule files as a container.

This is the simplest method to load rule into working memory.

```
KieContainer kc = ks.getKieClasspathContainer();
```

Using `KieRepository` to load a existing jar file.

```
KieRepository repo = ks.getRepository(); 
InputStream is = this.getClass().getResourceAsStream("/dtgov-workflows.jar"); 
KieModule kModule = repo.addKieModule(ks.getResources().newInputStreamResource(is)); 
```

[click here](https://github.com/droolsjbpm/drools/blob/master/drools-compiler/src/main/java/org/drools/compiler/kie/builder/impl/KieServicesImpl.java) to find more details about `KieServices`.

## 3.2 KieFileSystem

Get a KieFileSystem instance.

Using the KieFileSystem instance to load some static rule files or some rule string dynamically. 

```
KieFileSystem kfs = ks.newKieFileSystem();
```

calling method `generateAndWritePomXML` by passing a parameter `releaseId` ,type `ReleaseId` to generate a virtual pom file in working memory.

[click here](https://github.com/droolsjbpm/droolsjbpm-knowledge/blob/master/kie-api/src/main/java/org/kie/api/builder/ReleaseId.java) to find more details about type `ReleaseId`.

or calling `writePomXML` to generate a virtual pom file in working memory.

```
kfs.generateAndWritePomXML(ReleaseId releaseId);
kfs.writePomXML(String content);
```

calling the `writeKModuleXML` method by passing a parameter `content` to write `kmodule.xml` file into the KieFileSystem.

The content's value will be generated by KieModuleModel. More details are shown below.

```
kfs.writeKModuleXML(String content);
```

calling the `write` method by passing two parameters `path` and `content` to write rule String into the KieFileSystem.

```
kfs.write(String path, String content);
```

**Note:Actually, the KieFileSystem is a virtul file system, and it won't write files into the disk.**

[click here](https://github.com/droolsjbpm/droolsjbpm-knowledge/blob/master/kie-api/src/main/java/org/kie/api/builder/KieFileSystem.java) to find more details about `KieFileSystem`.

## 3.3 KieModuleModel

KieModuleModel is a model allowing to programmatically define a KieModule.

Get a KieModuleModel instance by KieServices, and next to configure it. But because the KieBaseModel is a part of it, so we should first get a KieBaseModel instance and configure it.The more details are shown below.

```
KieModuleModel module = ks.newKieModuleModel();
```
[click here](https://github.com/droolsjbpm/drools/blob/master/drools-compiler/src/main/java/org/drools/compiler/kproject/models/KieModuleModelImpl.java) to find more details about `KieModuleModel`.

## 3.4 KieBaseModel

Get a KieBaseModel named `kBase1` instance by KieModuleModel.

```
KieBaseModel defaultBase = module.newKieBaseModel("kBase1");
```

Configure it.

```
defaultBase.setEventProcessingMode(EventProcessingOption.STREAM).setDefault(true);
defaultBase.newKieSessionModel("defaultKSession").setDefault(true);
```

[click here](https://github.com/droolsjbpm/drools/blob/master/drools-compiler/src/main/java/org/drools/compiler/kproject/models/KieBaseModelImpl.java) to find more details about `KieBaseModel`.

## 3.5 KieBuilder

When the KieModuleModel and KieBaseModel are configured well, the KieFileSystem writed them into it, the KieBuilder instance will be generated by calling KieServices.`newKieBuilder`.

```
KieBuilder kb = ks.newKieBuilder(kfs);
```

Then we can get a KieModule instace:

```
kb.buildAll();
KieModule km = kb.getKieModule();
```

[click here](https://github.com/droolsjbpm/drools/blob/master/drools-compiler/src/main/java/org/drools/compiler/kie/builder/impl/KieBuilderImpl.java) to find more details about `KieBuilder`.

## 3.6 KieModule

As the above shown, this is the reason for KieModule generated.

```
KieModule km = kb.getKieModule();
```

[click here](https://github.com/droolsjbpm/drools/blob/master/drools-compiler/src/main/java/org/drools/compiler/kie/builder/impl/MemoryKieModule.java) to find more details about `KieModule`.

## 3.7 KieContainer

Get a KieContainer instance.

```
KieContainer kc = ks.newKieContainer(km.getReleaseId());
```

[click here](https://github.com/droolsjbpm/drools/blob/master/drools-compiler/src/main/java/org/drools/compiler/kie/builder/impl/KieContainerImpl.java) to find more details about `KieContainer`.


## 3.8 KieBase

Get a KieBase instace.

```
KieBase kBase = kc.getKieBase();
KieBase kBase1 = kc.getKieBase(String kBaseName);
```
[click here](https://github.com/droolsjbpm/droolsjbpm-knowledge/blob/master/kie-api/src/main/java/org/kie/api/KieBase.java) find more details about `KieBase`.


## 3.9 KieSession

Get a KieSession instance.

```
KieSession ksession = kBase.newKieSession();
```

Using the method `insert`, `fireAllRules` to matching data.

```
ksession.insert(Object obj);
ksession.fireAllRules();
ksession.dispose();
```

[click here](https://github.com/droolsjbpm/droolsjbpm-knowledge/blob/master/kie-api/src/main/java/org/kie/api/runtime/KieSession.java) find more details about `KieSession`.

# 4.  Simple Demo based Drools 6.5

## 4.1 Rule Class Definition:

### 4.1.1 RuleUnit Class:

```
package com.max.Drools_rule_demo_2.data.jpa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.max.Drools_rule_demo_2.data.jpa.enumeration.FieldType;
import com.max.Drools_rule_demo_2.data.jpa.enumeration.OperatorType;

@Entity
@Table(name = "rule_unit")
public class RuleUnit implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable = false)
	private String fieldName;
	
	@Column(nullable = false)
	private OperatorType operator;
	
	@Column(nullable = false)
	private String fieldValue;
	
	@Column(nullable = false)
	private FieldType fieldType;
	
	@ManyToOne
	@JoinColumn(name = "rule_id")
	private Rule rule;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public OperatorType getOperator() {
		return operator;
	}
	public void setOperator(OperatorType operator) {
		this.operator = operator;
	}
	public FieldType getFieldType() {
		return fieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
}
```

### 4.1.2 ResultUnit Class:

```
package com.max.Drools_rule_demo_2.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.max.Drools_rule_demo_2.data.jpa.enumeration.FieldType;
import com.max.Drools_rule_demo_2.data.jpa.enumeration.OperatorType;

@Entity
@Table(name = "result_unit")
public class ResultUnit {
	
	@Id
	@GeneratedValue
	private int id;
	@Column(nullable = false)
	private String fieldName;
	@Column(nullable = false)
	private String fieldValue;
	@Column(nullable = false)
	private FieldType fieldType;
	@ManyToOne
	@JoinColumn(name = "result_id")
	private Result result;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public FieldType getFieldType() {
		return fieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
```

### 4.1.3 Result Class:

```
package com.max.Drools_rule_demo_2.data.jpa.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "result")
public class Result {
	
	@Id
	@GeneratedValue
	private int id;
	@Column(nullable = false)
	private String name;
	@OneToMany(mappedBy = "result", cascade = CascadeType.PERSIST)
	private List<ResultUnit> resultUnitList;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ResultUnit> getResultUnitList() {
		return resultUnitList;
	}
	public void setResultUnitList(List<ResultUnit> resultUnitList) {
		this.resultUnitList = resultUnitList;
	}
}
```

### 4.1.4 Rule Class:

```
package com.max.Drools_rule_demo_2.data.jpa.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rule")
public class Rule implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@OneToOne
	@JoinColumn(name="result_id")
	private Result result;
	
	@OneToMany(mappedBy = "rule", cascade = CascadeType.PERSIST)
	private List<RuleUnit> ruleUnitList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<RuleUnit> getRuleUnitList() {
		return ruleUnitList;
	}
	public void setRuleUnitList(List<RuleUnit> ruleUnitList) {
		this.ruleUnitList = ruleUnitList;
	}
}
```

## 4.2 Initializing Rule Data:

```
	// 进行规则的创建、保存
	public Rule saveRule() {
		Rule rule = new Rule();

		/**
		 * 此处为 design_time 如果要 run_time 则需要获取"个数"参数
		 */
		RuleUnit ruleUnit1 = new RuleUnit();
		RuleUnit ruleUnit2 = new RuleUnit();
		RuleUnit ruleUnit3 = new RuleUnit();
		RuleUnit ruleUnit4 = new RuleUnit();
		RuleUnit ruleUnit5 = new RuleUnit();

		ruleUnit1.setFieldName("name");
		ruleUnit1.setOperator(OperatorType.EQUAL);
		ruleUnit1.setFieldValue("IBM");
		ruleUnit1.setFieldType(FieldType.STRING);
		ruleUnit1.setRule(rule);

		ruleUnit2.setFieldName("amount");
		ruleUnit2.setOperator(OperatorType.BIGGER);
		ruleUnit2.setFieldValue("2222");
		ruleUnit2.setFieldType(FieldType.INT);
		ruleUnit2.setRule(rule);

		ruleUnit3.setFieldName("date");
		ruleUnit3.setOperator(OperatorType.EQUAL);
		ruleUnit3.setFieldValue("2017-02-02");
		ruleUnit3.setFieldType(FieldType.STRING);
		ruleUnit3.setRule(rule);

		ruleUnit4.setFieldName("type");
		ruleUnit4.setOperator(OperatorType.EQUAL);
		ruleUnit4.setFieldValue("SAP");
		ruleUnit4.setFieldType(FieldType.STRING);
		ruleUnit4.setRule(rule);

		ruleUnit5.setFieldName("cost");
		ruleUnit5.setOperator(OperatorType.EXISTS_IN);
		ruleUnit5.setFieldValue("100,200,300,400,500");
		ruleUnit5.setFieldType(FieldType.LIST);
		ruleUnit5.setRule(rule);

		ruleUnitList.add(ruleUnit1);
		ruleUnitList.add(ruleUnit2);
		ruleUnitList.add(ruleUnit3);
		ruleUnitList.add(ruleUnit4);
		ruleUnitList.add(ruleUnit5);
		
		rule.setRuleUnitList(ruleUnitList);
		rule.setName("rule Demo2");

		ruleMap.put("rule Demo2", rule);
		
		Result result = new Result();
		result.setName("result 1");
		ResultUnit resultUnit1 = new ResultUnit();
		resultUnit1.setFieldName("entitlementType");
		resultUnit1.setFieldType(FieldType.STRING);
		resultUnit1.setFieldValue("TRAINING");
		resultUnit1.setResult(result);

		ResultUnit resultUnit2 = new ResultUnit();
		resultUnit2.setFieldName("documentInfo");
		resultUnit2.setFieldType(FieldType.DOCUMENTINFO);
		resultUnit2.setFieldValue("DocumentInfo");
		resultUnit2.setResult(result);

		ResultUnit resultUnit3 = new ResultUnit();
		resultUnit3.setFieldName("documentInfoList");
		resultUnit3.setFieldType(FieldType.DOCUMENTINFOLIST);
		resultUnit3.setFieldValue("documentInfoList");
		resultUnit3.setResult(result);

		List<ResultUnit> resultUnitList = new ArrayList<ResultUnit>();
		resultUnitList.add(resultUnit1);
		resultUnitList.add(resultUnit2);
		resultUnitList.add(resultUnit3);

		result.setResultUnitList(resultUnitList);
		rule.setResult(result);
		resultRepository.save(result);
		return ruleRepositiry.save(rule);
	}
```

## 4.3 initializing User Input Data:

```
	public void initDocumentInfo() {
		// 构造用户对象数据
		documentInfo1 = new DocumentInfo();
		documentInfo1.setId(0);
		documentInfo1.setName("IBM");
		documentInfo1.setType("SAP");
		documentInfo1.setDate("2017-02-02");
		documentInfo1.setAmount(6666);
		List<String> costStr = new ArrayList<String>();
		costStr.add("100");
		documentInfo1.setCost(costStr);
	}

```

## 4.4 Rule Matching:

```
package com.max.Drools_rule_demo_2.data.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.max.Drools_rule_demo_2.data.jpa.domain.DocumentInfo;
import com.max.Drools_rule_demo_2.data.jpa.domain.EntitlementInfo;
import com.max.Drools_rule_demo_2.data.jpa.global.GlobaleEntitlementHelper;
import com.max.Drools_rule_demo_2.data.jpa.util.RuleStrUtil;


@Service
public class MainService {
	
	private DocumentInfo documentInfo1;
	private List<String> ruleStrList;
	
	
	@Autowired
	private BasicRuleService bRuleService;
	
	@Autowired
	private GlobaleEntitlementHelper helper;
	
	
	public void initDocumentInfo() {
		// 构造用户对象数据
		documentInfo1 = new DocumentInfo();
		documentInfo1.setId(0);
		documentInfo1.setName("IBM");
		documentInfo1.setType("SAP");
		documentInfo1.setDate("2017-02-02");
		documentInfo1.setAmount(6666);
		List<String> costStr = new ArrayList<String>();
		costStr.add("100");
		documentInfo1.setCost(costStr);
	}
	
	public DocumentInfo getDocumentInfo1() {
		return documentInfo1;
	}

	public EntitlementInfo SimpleRuleProcess() {
		if (documentInfo1 == null) {
			initDocumentInfo();
		}
		
		ruleStrList = RuleStrUtil.getRuleStrList(bRuleService.getRuleList());
		
		/*
		 * 加载规则:
		 * 其中 KieServices.Factory.get() 是利用一个内部静态类来实现对KieService单例的生成
		 * 有关利用静态内部类生成单例的参考文章:
		 * http://blog.csdn.net/nsw911439370/article/details/50456231
		 */
		KieServices ks = KieServices.Factory.get();
		//通过函数loadRuleStr获得加载规则之后得到的KieModule
		KieModule km = loadRuleStr(ks, ruleStrList);
		KieContainer kc = ks.newKieContainer(km.getReleaseId());
		KieBase kBase = kc.getKieBase();
		KieSession ksession = kBase.newKieSession();
		// 进行匹配，并产生最后的result
		ksession.setGlobal("helper", helper);
		FactHandle hf = ksession.insert(getDocumentInfo1());
		ksession.fireAllRules();
		ksession.dispose();
		
		System.out.println(helper.getEntitlementInfo().getDocumentInfoList().size());
		return helper.getEntitlementInfo();
	}
	

	private KieModule loadRuleStr(KieServices ks, List<String> ruleStrList) {
		
		//直接调用 kieServicesImpl.newKieFileSystem()来实例化一个KieFileSystemImpl
		KieFileSystem kfs = ks.newKieFileSystem();
		
		/*
		 * 直接调用 kieServicesImpl.newKieModuleModel()来实例化一个KieModuleModel
		 * 
		 * ========================================
		 * KieModuleModel 和 KieModule的区别是什么？
		 * 
		 * KieModuleModel is a model allowing to programmatically define a KieModule
		 * 
		 * A KieModule is a container of all the resources necessary to define a set of KieBases like
		 * a pom.xml defining its ReleaseId, a kmodule.xml file declaring the KieBases names and configurations
		 * together with all the KieSession that can be created from them and all the other files
		 * together with all the KieSession that can be created from them and all the other files
		 *  
		 * ========================================
		 * 
		 */
		KieModuleModel module = ks.newKieModuleModel();

		/*
		 * 在newKieBaseModel方法中，通过传入参数name来实例化一个KieModuleModel
		 * KieBaseModel kbase = new KieBaseModelImpl(this, name);
		 * this 指的是 KieModuleModel对象module
		 * module对象内部维护了一个KieBaseModel的Map集合
		 * private Map<String, KieBaseModel> kBases = new HashMap<String, KieBaseModel>();
		 * 每次实例化KieBaseModel，就更新kBases集合
		 */
		KieBaseModel defaultBase = module.newKieBaseModel("kBase1");
		
		/*
		 * EventpRrocessingMode分为两种：CLOUD，STREAM
		 */
		defaultBase.setEventProcessingMode(EventProcessingOption.STREAM).setDefault(true);
		
		/*
		 *在KieBaseModelImpl内部直接:
		 *KieSessionModel kieSessionModel = new KieSessionModelImpl( this, name );
		 *和KieModuleModel一样，
		 *在KieBaseModelImpl内部维护了一个Map集合：
		 *private Map<String, KieSessionModel> kSessions = new HashMap<String, KieSessionModel>();
		 *每次实例化KieSessionModel，就更新kSessions集合
		 */
		defaultBase.newKieSessionModel("defaultKSession").setDefault(true);

		/*
		 * 通过KieModuleModel内部的一个kModuleMarshaller静态类，实现XML的转换
		 * XML的内容就一部分就是由kieModuleModel和KieBaseModel生成的
		 */
		System.out.print("the module parse XML is :");
		System.out.println(module.toXML());
		/*
		 *KieFileSystemImpl内部封装了一个MemoryFileSystem
		 *kieFileSystem.writeKModuleXML -> memoryFileSystem.write(String path,byte[] content,boolean isCreateFolder)
		 *默认path为： src/resources/METE-INF/kmodule.xml
		 *注：执行write操作，并不会生成物理文件，只是将其保存在了一个Map<String,byte[]>中
		 */
		kfs.writeKModuleXML(module.toXML());

		for (int i = 0; i < ruleStrList.size(); i++) {
			String path = "src/main/resources/rule/ruleDemo" + i + ".drl";
			
			/*
			 * 写入方式与调用writeModuleXML方法一样。
			 * 不过这里给了一个path
			 */
			kfs.write(path, ruleStrList.get(i));
		}

		KieBuilder kb = ks.newKieBuilder(kfs);
		kb.buildAll();
		if (kb.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
			System.out.println(kb.getResults().toString());
		}
		return kb.getKieModule();
	}
}
```

## 4.5 Calling controller to test:

```
package com.max.Drools_rule_demo_2.data.jpa.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.max.Drools_rule_demo_2.data.jpa.domain.EntitlementInfo;
import com.max.Drools_rule_demo_2.data.jpa.domain.Rule;
import com.max.Drools_rule_demo_2.data.jpa.service.BasicRuleService;
import com.max.Drools_rule_demo_2.data.jpa.service.MainService;
import com.max.Drools_rule_demo_2.data.jpa.util.RuleStrUtil;

@Controller
public class DroolsDemoTest1Controller {
	
	
	@Autowired
	private MainService mainSevice;
	@Autowired
	private BasicRuleService bRuleService;
	
	@RequestMapping("/processRule")
	@ResponseBody
	public EntitlementInfo droosTest1() {
		return mainSevice.SimpleRuleProcess();
	}
	
	@RequestMapping("/saveRule")
	@ResponseBody
	public String saveRule() {
		Rule rule = bRuleService.saveRule();
		if (rule != null) {
			return "success"; 
		}
		else {
			return "failed";
		}
	}
	
	@RequestMapping("/getRule")
	@ResponseBody
	public List<String> getRule() {
		List<String> rule = RuleStrUtil.getRuleStrList(bRuleService.getRuleList());
		if (rule != null) {
			return rule; 
		}
		else {
			return null;
		}
	}
}
```

# 5. Get demo code from github

[click here](https://github.com/clayclayclay/DroolsDemo) to get demo code.

 
