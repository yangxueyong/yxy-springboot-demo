insert into t_p_cust_prod(cust_no,main_prod_no,num,amount)
 select cust_no,main_prod_no,count(*) num, sum(amount) as amount
 from t_p_cust_deposit_prod group by cust_no,main_prod_no