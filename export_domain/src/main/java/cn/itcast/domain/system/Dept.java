package cn.itcast.domain.system;

//部门的实体类
public class Dept {

    private String id;

    private String deptName;  //北京事业部    dept.parentId  100 ,如果我们需要知道该部门的父部名称那应该如何？


    //private String parentId;  //父部门的id. 这样子编写实体类的弊端： 显示数据不方便。  100
                               //java是面向对象的语言，所有的事物都想使用对象去描述。
    private Dept parent;  //父部门对象。   dept.parent.deptName

    private String state;
    private String companyId;
    private String companyName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Dept getParent() {
        return parent;
    }

    public void setParent(Dept parent) {
        this.parent = parent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
