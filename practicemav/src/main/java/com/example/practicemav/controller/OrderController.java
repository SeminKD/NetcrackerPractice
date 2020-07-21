package com.example.practicemav.controller;


import com.example.practicemav.model.DBOrder;
import com.example.practicemav.model.Exhibition;
import com.example.practicemav.model.Queries;
import com.example.practicemav.model.User;
import com.example.practicemav.repository.ExRepository;
import com.example.practicemav.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    ExRepository exRepository;
    
    @GetMapping("/orders")
    public String starting(Model model, @AuthenticationPrincipal User user){
        List<Queries> orders=getUserOrders(user.getUser_id());
        if(!orders.isEmpty()){
        model.addAttribute("orders",orders);
        }
        return "order";
    }

    @GetMapping("/order-delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id){
        orderRepo.deleteById(id);
        return "redirect:/orders";
    }

    @GetMapping("/order-edit/{id}")
    public String updateOrderForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("ID",id);
        List<Exhibition> ex = exRepository.findAll();
        model.addAttribute("exs",ex);
        return "order-edit";
    }

    @PostMapping("/order-edit")
    public String updateUser(@RequestParam(value = "ex_name") String ex_name,
                             @RequestParam(value = "amount") int amount,
                             @RequestParam(value = "id") Long order_id
                             ){
        String date=new Date().toString();
        Long exId = getExId(ex_name);
        double pr = amount * getExPrice(ex_name);
        DBOrder dbOrder = new DBOrder();

        String query = "update orders set order_time="+"'"+date+
                "',ex_id="+exId+",amount="+amount+",order_price="+pr+" where order_id="+order_id;
        try {
            Statement statement = dbOrder.getConnection().createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
        return "redirect:/orders";
    }

    @GetMapping("/add-order")
    public String adding(Model model){
        List<Exhibition> ex = exRepository.findAll();
        model.addAttribute("exs",ex);
        return "add-order";
    }

    @PostMapping("/add-order")
    public String addOrder(@RequestParam(value = "ex_name") String ex_name,
                           @RequestParam(value = "amount") int amount,
                           @AuthenticationPrincipal User user) {

        String date=new Date().toString();
        Long lastId = getLastId()+1;
        Long exId = getExId(ex_name);
        double pr = amount * getExPrice(ex_name);
        long userId = user.getUser_id();

        DBOrder dbOrder = new DBOrder();
        String query = "insert orders(order_id, order_time, amount, order_price, ex_id, user_id)" +
                "values ("+lastId+","+"'"+date+"',"+amount+","+pr+","+exId+","+userId+")";
        try {
            Statement statement = dbOrder.getConnection().createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
        return "redirect:/orders";
    }

    private Long getLastId(){
        DBOrder dbOrder=new DBOrder();
        String a="select MAX(order_id) from orders";
        Long id = null;
        try {
            Statement statement = dbOrder.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(a);
            resultSet.next();
            id = resultSet.getLong(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    private double getExPrice(String name){
        DBOrder dbOrder=new DBOrder();
        String query="select ex_price from exhibitions where ex_name="+"'"+name+"'";
        Long id = null;
        try {
            Statement statement = dbOrder.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getLong(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    private Long getExId(String name){
        DBOrder dbOrder=new DBOrder();
        String query="select ex_id from exhibitions where ex_name="+"'"+name+"'";
        Long id = null;
        try {
            Statement statement = dbOrder.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getLong(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    private List<Queries> getUserOrders(Long user_id){
        DBOrder dbOrder = new DBOrder();
        String query="select order_id,order_time, ex_name, amount, order_price from " +
                "users inner join orders o on users.user_id = o.user_id " +
                "inner join exhibitions e on o.ex_id = e.ex_id " +
                "where o.user_id="+user_id;
        List<Queries> list = new ArrayList<>();
        try{
            Statement statement = dbOrder.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                list.add(new Queries(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getDouble(5)
                ));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
