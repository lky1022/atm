package src.com.lky.atm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private  ArrayList<src.com.lky.atm.Account> accounts=new ArrayList<>();
    private src.com.lky.atm.Account loginacc;

    private Scanner sc=new Scanner(System.in);

    public void start(){
        while (true) {
            System.out.println("===欢迎进入ATM系统===");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择:");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                default:
                    System.out.println("输入错误没有该操作");
            }
        }
    }

    private void login(){
        System.out.println("===登录程序===");
        if (accounts.size()==0){
            System.out.println("没有任何数据，请先添加数据");
            return;
        }
        while (true){
            System.out.println("请输入您的账户卡号");
            String cardId=sc.next();
            Account acc=getAccountByCardId(cardId);
            if (acc==null){
                System.out.println("您输入的卡号有误，请重新输入");
            }else {
                while (true) {
                    System.out.println("请您输入您的账户密码");
                    String password = sc.next();
                    if (acc.getPassword().equals(password)) {
                        System.out.println("恭喜"+acc.getName()+"登录成功"+"，您的卡号是"+acc.getCardId());
                        loginacc=acc;
                        //操作界面
                        showcommand();
                        return;

                    }else {
                        System.out.println("您输入的密码有误，请重新输入");
                    }
                }
            }
        }
    }

    //操作界面
    private void showcommand() {
        while (true) {
            System.out.println(loginacc.getName() + "您可以选择如下功能进行账户处理");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.密码修改");
            System.out.println("6.退出");
            System.out.println("7.注销账户");
            System.out.println("请选择您想要的操作");
            int choice = sc.nextInt();
            switch (choice) {
                case 1://查询账户
                    showloginAccount();
                    break;
                case 2://存款
                    depositMoney();
                    break;
                case 3://取款
                    drawMoney();
                    break;
                case 4://转账
                    transferMoney();
                    break;
                case 5://密码修改
                    updatapasswprd();
                    return;
                case 6://退出
                    System.out.println(loginacc.getName()+"您退出系统成功");
                    return;
                case 7://注销账户
                    if(deleteAccount()){
                        return;
                    }
                    break;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }
        }
    }

    private void updatapasswprd() {
        while (true) {
            System.out.println("===用户密码修改===");
            System.out.println("请您输入原密码");
            String password1 = sc.next();
            if (loginacc.getPassword().equals(password1)) {
                while (true) {
                    System.out.println("请您输入您想修改后的密码");
                    String passwd2 = sc.next();
                    System.out.println("请您输入再次修改后的密码");
                    String passwd3 = sc.next();
                    if (passwd2.equals(passwd3)) {
                        loginacc.setPassword(passwd2);
                        System.out.println("密码修改成功");
                        return;
                    } else {
                        System.out.println("两次输入不一致，请重新确定新密码");
                    }
                }
            } else {
                System.out.println("原密码错误，请您重新输入");
            }
        }
    }

    //销户
    private boolean deleteAccount() {
        System.out.println("===销户系统===");
        System.out.println("请问您确认销户,Y/N");
        String choice = sc.next();
        switch (choice) {
            case "Y":
                if(loginacc.getMoney()==0){
                    accounts.remove(loginacc);
                    System.out.println("您好，您的账户以成功销户");
                    return true;
                }else {
                    System.out.println("对不起您的账户存在金额，销户失败");
                    return false;
                }
            default:
                System.out.println("您的销户失败");
                return false;
        }
    }

    private void transferMoney() {
        System.out.println("===用户转账===");
        if (accounts.size() < 2) {
            System.out.println("当前只有一个用户无法转账");
            return;
        }
        if (loginacc.getMoney() == 0) {
            System.out.println("您的账户余额为0，无法执行转账操作");
            return;
        }
        while (true) {
            System.out.println("请您输入对方的卡号");
            String cardId = sc.next();
            Account acc = getAccountByCardId(cardId);
            if (acc == null) {
                System.out.println("您输入的对方卡号有误");
            }else {
                String name=  "*"+acc.getName().substring(1);
                System.out.println("请您输入"+name+"的姓氏");
                String pre=sc.next();
                if (acc.getName().startsWith(pre)) {
                    while (true){
                    System.out.println("请您输入您转账的金额：");
                    double money=sc.nextDouble();
                    if(money<=loginacc.getMoney()){
                        loginacc.setMoney(loginacc.getMoney()-money);
                        acc.setMoney(acc.getMoney()+money);
                        System.out.println("恭喜您转账成功");
                        return;
                    }else {
                        System.out.println("您的余额不足，最多可以转账金额为："+loginacc.getMoney());
                    }}
                }else {
                    System.out.println(" 对不起您输入的姓氏有问题");
                }
            }
        }
    }

    private void drawMoney() {
        System.out.println("===取取钱操作===");
        if (loginacc.getMoney() < 100) {
            System.out.println("您的账户余额不足100，无法执行次操作");
            return;
        }
        while (true) {
            System.out.println("请你输入你的取钱金额");
            double money = sc.nextDouble();
            if (money <= loginacc.getMoney()) {
                if (money >loginacc.getLimit()) {
                    System.out.println("您当前取款金额超过每次限额，您每次最多可取" + loginacc.getLimit());

                } else {
                    loginacc.setMoney( loginacc.getMoney()-money);
                    System.out.println("恭喜您取钱成功" + " 目前金额为" + loginacc.getMoney());
                    return;
                }
            } else {
                System.out.println("余额不足，您的账户余额为" + loginacc.getMoney());
            }
        }
    }

    //查询账户
    private void showloginAccount(){
        System.out.println("==="+loginacc.getName()+"您的账户信息为===");
        System.out.println("卡号是："+loginacc.getCardId());
        System.out.println("户主是："+loginacc.getName());
        System.out.println("余额是："+loginacc.getMoney());
        System.out.println("取现额度为："+loginacc.getLimit());
    }

    //存钱
    private void depositMoney(){
        System.out.println("===存钱操作===");
        System.out.println("请你输入你的存钱金额");
        double money=sc.nextDouble();

        loginacc.setMoney(money+ loginacc.getMoney());
        System.out.println("恭喜您存钱成功"+" 目前金额为"+loginacc.getMoney());
    }

    private void createAccount(){
        System.out.println("===开户界面===");
        Account acc=new Account();
        System.out.println("请输入你的账户名称");
        String name=sc.next();
        acc.setName(name);

        while (true) {
            System.out.println("请输入您的性别");
            char sex = sc.next().charAt(0);
            if (sex == 'n' || sex == 'w') {
                acc.setSex(sex);
                break;
            } else {
                System.out.println("你输入的性别有误，请重新输入");
            }
        }

        while (true) {
            System.out.println("请您输入的账户密码");
            String password1 = sc.next();
            System.out.println("请您再次确认您的账户密码");
            String password2 = sc.next();
            if (password1.equals(password2)) {
                acc.setPassword(password1);
                break;
            }else {
                System.out.println("您两次输入的密码有错误，请重新输入");
            }
        }

        System.out.println("请您输入的每次提取额度");
        double limit= sc.nextDouble();
        acc.setLimit(limit);

        String newcardId=creatCardId();
        acc.setCardId(newcardId);

        acc.setMoney(0);
        accounts.add(acc);
        System.out.println("恭喜您"+acc.getName()+"开户成功"+"您的卡号为："+newcardId);
    }

    private String creatCardId() {
        while (true) {
            Random r = new Random();
            String cardId = "";
            for (int i = 0; i < 8; i++) {
                int a =r.nextInt(10);
                cardId += a;
            }
            Account acc = getAccountByCardId(cardId);
            if (acc == null) {
                return cardId;
            }
        }
    }

    private Account getAccountByCardId(String cardId){
        for (int i=0;i< accounts.size();i++){
            Account acc=accounts.get(i);
            if (acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;
    }
}
