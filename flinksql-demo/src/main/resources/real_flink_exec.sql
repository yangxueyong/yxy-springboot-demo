insert into t_p_cust_red_money(cust_no,prod_no,red_money,create_time)
 select k.cust_no,'Q00100101' as prod_no,trade_money * 0.2 as trade_money, localTimestamp as create_time
 from kafka_cust_red_money k inner join t_p_cust_main for system_time as of k.proctime as m on k.cust_no = m.cust_no
     left join t_p_cust_red_money for system_time as of k.proctime as r on k.cust_no = r.cust_no
     where r.cust_no is null and k.trade_money>10 and k.trade_channel='wx'