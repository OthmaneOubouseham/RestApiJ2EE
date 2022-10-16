package rest.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rest.exception.DataNotFoundException;
import rest.model.Rate;
import rest.model.util.Timestamp;
import rest.service.util.Constants;
import rest.util.DB_web_services;

public class RateService {
		
	private Map<Long, Rate> rates = DB_web_services.getRates();
	
	
	public RateService()
			throws SQLException
	{		
		try(DB_web_services db = new DB_web_services()){
	    	
	    	PreparedStatement ppsm = db.getPreparedStatement(Constants.Rate.getAll);
	    	
	    	ResultSet rs = ppsm.executeQuery();
	    	this.rates.clear();
	    	
	    	while(rs.next()){
	    		this.rates.put(rs.getLong("ID_rate"), new Rate(rs.getLong("ID_rate"), rs.getInt("value"), new Timestamp(rs.getString("date_creation")), rs.getLong("ID_user"), rs.getLong("ID_multimedia")));
	    	}
		}
	}
	
	
	

	public List<Rate> getAllRates()
	{
		List<Rate> return_rates = new ArrayList<Rate>(this.rates.values());
		
		if(return_rates.isEmpty())
			throw new DataNotFoundException("No rates was found !");
		
		
    	return return_rates;		
	}
	

	public Rate getRate(long id){
		Rate rate = rates.get(id);

		if(rate == null)
			throw new DataNotFoundException("The rate with the id `" + id + "` was not found !");
		
		
		return rate;
	}
	
	
	public Rate addRate(int value, long id_user, long id_multimedia) 
			throws SQLException{
		
		try(DB_web_services db = new DB_web_services()){
    	
	    	PreparedStatement ppsm = db.getPreparedStatement(Constants.Rate.post);
	    	
	    	ppsm.setInt(1, value);
	    	ppsm.setLong(2, id_user);
	    	ppsm.setLong(3, id_multimedia);
	    	
	    	
	    	int rs = ppsm.executeUpdate();
	
	    	if(rs == 1){
	    		ResultSet generated_id = ppsm.getGeneratedKeys();
	    		
	    		if(generated_id.next()){
	    			ppsm = db.getPreparedStatement(Constants.Rate.getByID);
	    			
	    			ppsm.setLong(1, generated_id.getLong(1));
	    			
	    			ResultSet rs_rate = ppsm.executeQuery();
	    	    	
	    	    	if(rs_rate.next()){
	    	    		Rate rate = new Rate(
	    	    				rs_rate.getLong("ID_rate"),
	    	    				rs_rate.getInt("value"),
	    	    				new Timestamp(rs_rate.getString("date_creation")),
	    	    				rs_rate.getLong("ID_user"),
	    	    				rs_rate.getLong("ID_multimedia"));
	    	    		
	    	    		this.rates.put(rs_rate.getLong("ID_rate"), rate);
	    	    		
	    	    		return rate;
	    	    	} 
	    		}
	    	}
	
	    	
	    	return null;
		}
	}
	
	
	public boolean updateRate(long id, int value) 
			throws SQLException{

		try(DB_web_services db = new DB_web_services()){
			

			PreparedStatement ppsm = db.getPreparedStatement(Constants.Rate.putByID);
				
			ppsm.setInt(1, value);
			ppsm.setLong(2, id);

			
			int rs = ppsm.executeUpdate();

			if(rs == 1){
				Rate rate = getRate(id);
				
				rate.setValue(value);
			}
			
			
			return (rs == 1) ? true : false;
		}
	}
	
	
	public boolean removeRate(long id) 
			throws SQLException{
		try(DB_web_services db = new DB_web_services()){

			PreparedStatement ppsm = db.getPreparedStatement(Constants.Rate.deleteByID);
	
	    	ppsm.setLong(1, id);
	    	
	    	int rs = ppsm.executeUpdate();
	
	    	if(rs == 1){
	    		this.rates.remove(id);
	    	}
	    	
	    	
	    	return (rs == 1) ? true : false;
		}
	}
	
}
