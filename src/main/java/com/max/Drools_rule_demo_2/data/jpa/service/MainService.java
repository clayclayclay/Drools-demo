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
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
//		ksession.setGlobal("cost", list);
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
		 * 
		 * ========================================
		 * 具体区别是什么？
		 * ========================================
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
