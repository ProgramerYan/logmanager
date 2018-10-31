package com.yjw.springtest.web;

import com.yjw.springtest.dao.*;
import com.yjw.springtest.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试提交
 */
@RestController
public class ServiceGroupController {
    @Autowired
    private ServiceGroupRepository serviceGroupRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ServiceGroupSecondRepository serviceGroupSecondRepository;
    @Autowired
    private TreemenuRepository treemenuRepository;

    /**
     * 获取全部服务群组信息
     * @return
     */
    @GetMapping("/getServiceGroup")
    public List<Servicegroup> getServiceGroup() {
        List<Servicegroup> servicegroupList = serviceGroupRepository.findAll();
        return servicegroupList;
    }

    /**
     * 获取服务子类表中所有信息
     * @return
     */
    @GetMapping("/getServiceGroupSecond")
    public List<Servicegroupsecond> getServiceGroupSecond() {
        List<Servicegroupsecond> servicegroupsecondList = serviceGroupSecondRepository.findAll();
        return servicegroupsecondList;
    }

    /**
     * 获得树形菜单
     * @return
     */
    @PostMapping("/getTree")
    public List<Treemenu> testTree() {
        List<Treemenu> treemenuList = treemenuRepository.findAll();
        return treemenuList;
    }


    /**
     * 新建父类文件
     * @return
     */
    @GetMapping("/addServiceGroup")
    public boolean addServiceGroup(@RequestParam("filename") String filename) {
        Treemenu treemenu = treemenuRepository.findByName(filename);
        if (treemenu != null) {
            return false;
        }
        Treemenu treemenu2 = new Treemenu();
        treemenu2.setParentid(0);
        treemenu2.setName(filename);
        treemenuRepository.save(treemenu2);
        return true;
    }

    /**
     * 新建文件
     * @return
     */
    @GetMapping("/addGroupChild")
    public boolean addGroupChild(@RequestParam("fathername") String fathername,@RequestParam("filename") String filename) {
        if(filename.equals("")) {
            return false;
        }
        Treemenu old_treemenu = treemenuRepository.findByName(filename);
        if(old_treemenu != null) {
            return false;
        }
        Treemenu treemenu = treemenuRepository.findByName(fathername);
        Treemenu treemenu1 = new Treemenu();
        treemenu1.setName(filename);
        treemenu1.setParentid(treemenu.getId());
        treemenuRepository.save(treemenu1);
        return true;
    }

    /**
     * 删除文件
     * @return a
     */
    @GetMapping("/deleteServiceGroup")
    public boolean deleteServiceGroup (@RequestParam("nodename") String nodename) {
         Treemenu treemenu = treemenuRepository.findByName(nodename);
         ArrayList<Integer> arrayList = new ArrayList<>();
         arrayList.add(treemenu.getId());
         ArrayList<Integer> arrayList1 = deleteGroupChild(treemenu.getId(),arrayList);
         for(int i=0;i<arrayList1.size();i++) {
             treemenuRepository.deleteById(arrayList1.get(i));
         }
        return true;
    }
    public ArrayList<Integer> deleteGroupChild(int number,ArrayList arrayList1) {
            int sizenum = arrayList1.size();
            List<Treemenu> treemenuList = treemenuRepository.findByParentid(number);

            treemenuList.forEach(treemenu -> arrayList1.add(treemenu.getId()));
//            for(Treemenu treemenu : treemenuList) {
//                arrayList1.add(treemenu.getId());mmmmmmmmm
//            }
//            for(int j=0;j<treemenuList.size();j++){
//                arrayList1.add(treemenuList.get(j).getId());
//            }
            if(sizenum == arrayList1.size()){
                return arrayList1;
            }else {
                treemenuList.forEach(treemenu -> deleteGroupChild(treemenu.getId(),arrayList1));
//                for(int i=0;i<treemenuList.size();i++) {
//                    deleteGroupChild(treemenuList.get(i).getId(),arrayList1);
//                }
            }
            return arrayList1;
    }

    /**
     * 获得树形菜单
     * @return
     */
//    @PostMapping("/getTree")
//    public List<Tree> getTree() {
//        List<Tree> treeList = new ArrayList<>();
//        List<Servicegroup> servicegroupList = serviceGroupRepository.findAll();
//        for (int i=0;i<servicegroupList.size();i++) {
//            Tree tree = new Tree();
//            tree.setId(servicegroupList.get(i).getId());
//            tree.setParentId(servicegroupList.get(i).getId()+50);
//            tree.setName(servicegroupList.get(i).getGroupname());
//            treeList.add(tree);
//            List<Servicegroupsecond> servicegroupsecondList = serviceGroupSecondRepository.findByGroupname(servicegroupList.get(i).getGroupname());
//            if(servicegroupsecondList.size() != 0) {
//                for(int j=0;j<servicegroupsecondList.size();j++) {
//                    Tree tree1 = new Tree();
//                    tree1.setId(servicegroupList.get(i).getId()+200);
//                    tree1.setParentId(servicegroupList.get(i).getId());
//                    tree1.setName(servicegroupsecondList.get(j).getSecondname());
//                    treeList.add(tree1);
//                }
//            }
//        }
//        return treeList;
////        return "[{\"id\":3,\"parendId\":20,\"name\":\"Foods\"}," +
////                "{\"id\":4,\"parentId\":3,\"name\":\"Fruits\"}," +
////                "{\"id\":5,\"parentId\":4,\"name\":\"Fruits\"}" +
////                "]";
//    }

    /**
     * 下拉树形菜单
     * @return
     */
//    @GetMapping("/combotTree")
//    public List<CombotTree> combotTrees() {
//        List<CombotTree> combotTreeList = new ArrayList<>();
//        List<Servicegroup> servicegroupList = serviceGroupRepository.findAll();
//        for(int i=0;i<servicegroupList.size();i++) {
//            CombotTree combotTree = new CombotTree();
//            combotTree.setText(servicegroupList.get(i).getGroupname());
//            combotTree.setState("closed");
//            List<Servicegroupsecond> servicegroupsecondList = serviceGroupSecondRepository.findByGroupname(servicegroupList.get(i).getGroupname());
//            List<ChildrenInTree> childrenInTreeList = new ArrayList<>();
//            for(int j=0;j<servicegroupsecondList.size();j++) {
//                ChildrenInTree childrenInTree = new ChildrenInTree();
//                childrenInTree.setText(servicegroupsecondList.get(j).getSecondname());
//                childrenInTreeList.add(childrenInTree);
//            }
//            combotTree.setChildren(childrenInTreeList);
//            combotTreeList.add(combotTree);
//        }
//        return combotTreeList;
//    }
//
//    @GetMapping("/combotTreeOfSecondname")
//    public List<CombotTree> combotTreeOfSecondname() {
//        List<CombotTree> combotTreeList = new ArrayList<>();
//        List<Servicegroup> servicegroupList = serviceGroupRepository.findAll();
//        List<Servicegroupsecond> servicegroupsecondList = serviceGroupSecondRepository.findAll();
//        for(int i=0;i<servicegroupsecondList.size();i++) {
//            CombotTree combotTree = new CombotTree();
//            combotTree.setText(servicegroupsecondList.get(i).getSecondname());
//            combotTree.setState("closed");
//            combotTree.setChildren(null);
//            combotTreeList.add(combotTree);
//        }
//        return combotTreeList;
//    }

//    @GetMapping("/testLambda")
//    public void testLambda() {
////        List<String> stringList = Arrays.asList("3","2","1");
////        //stringList.forEach(x -> System.out.println(x));
////        List<String> stringList1 = stringList.stream()
////                .filter(x -> x.equals("3"))
////                .collect(Collectors.toList());
////        stringList1.forEach(x -> System.out.println(x));
//        //Test test = Test::new;
//    }
//    @FunctionalInterface
//    interface lambda {
//        default int a() {
//            return 1;
//        }
//         void print1();
//        //void print2();
//    }
}
