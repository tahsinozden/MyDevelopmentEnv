package ozden;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ozden.controllers.VoteService;
import ozden.entities.IPAddressToVoteTable;
import ozden.repos.IPAddressToVoteTableRepository;

@Component
public class IPAddressCheckInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private VoteService voteService;
	private String ip;
	private Integer tableID;
	private boolean checkIPFlag = false;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Inside the interceptor!");
		if (!request.getMethod().equals("POST")){
			System.out.println("Go on with the logic...");
			return true;
		}
		
		if (request.getParameter("checkIP") == null){
			System.out.println("Dont check ip, go on...");
			return true;
		}
		
		// make it true to enable IP check
		checkIPFlag = true;
		
		if (request.getParameter("ip") == null){
			System.out.println("IP is not provided!");
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Provide IP address!!!");
		}
		
		if (request.getParameter("tableID") == null){
			System.out.println("tableID is not provided!");
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Provide tableID!!!");
		}
		
		String ipAddr = request.getParameter("ip");
		Integer voteTableID = Integer.parseInt(request.getParameter("tableID"));
		this.ip = ipAddr;
		this.tableID = voteTableID;
		
		if (voteService.isIPaddrUsedForVotingBefore(ipAddr, voteTableID)){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "A vote is done with this IP address for the tableID: " + voteTableID);
		}

		System.out.println(ipAddr);
		System.out.println(voteTableID);
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		// ip not checked, nothing to save to the database.
		if (!checkIPFlag){
			return;
		}
		
		if (response.getStatus() == HttpStatus.OK.value()){
			System.out.println("Now saving the IP to the database...");
			IPAddressToVoteTable ip2table = new IPAddressToVoteTable(ip, tableID);
			voteService.saveIP2Table(ip2table);
		}
		else {
			System.out.println("error occured. code : " + response.getStatus());
		}
	}

}
