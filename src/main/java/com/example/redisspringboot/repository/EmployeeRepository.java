package com.example.redisspringboot.repository;

import com.example.redisspringboot.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;//crud hash
    private ListOperations listOperations;
    private SetOperations setOperations;



    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {
        this.setOperations = redisTemplate.opsForSet();
        this.listOperations = redisTemplate.opsForList();
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){
//        hashOperations.put("EMPLOYEE", employee.getId(), employee);
//        listOperations.rightPush("EMPLOYEE_LIST", employee);

        setOperations.add("EMPLOYEE_SET", employee);
    }
    public List<Employee> findAll(){
//        return hashOperations.values("EMPLOYEE");

//        return listOperations.range("EMPLOYEE_LIST", 0, -1);

        return Arrays.asList((Employee[]) setOperations.members("EMPLOYEE_SET").toArray());
    }
    public Employee findById(Integer id){
//        return (Employee) hashOperations.get("EMPLOYEE", id);

//        List<Employee> list = findAll();
//        for(Employee e : list) {
//            if(e.getId() == id)
//                return e;
//        };
//
//        return null;

        List<Employee> list = findAll();
        for(Employee e : list) {
            if(e.getId() == id)
                return e;
        };

        return null;
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
//        hashOperations.delete("EMPLOYEE", id);

//        listOperations.remove("EMPLOYEE_LIST", 1, findById(id));

        setOperations.remove("EMPLOYEE_SET", findById(id));
    }
}
