package com.imm.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;

public class SolrUtil {
	public static String url = "http://localhost:8090/solr/collection1";
	final static Log log = LogFactory.getLogger(SolrUtil.class);

	public static void indexFilesSolrCell(String fileName, String solrId, String type)
			 {
		SolrServer solr = new HttpSolrServer(url);
		ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
		String contentType = "";
		log.info("document type :" + type);
		if ("pdf".equals(type)) {
			contentType = "application/pdf";

		} else if ("word".equals(type)) {
			contentType = "application/pdf";
		} else {
			return;
		}
		try {
			up.addFile(new File(fileName), contentType);
			up.setParam("literal.id", solrId);
			up.setParam("uprefix", "attr_");
			up.setParam("fmap.content", "attr_content");
			up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);

			try {
				solr.request(up);
			} catch (SolrServerException e) {
				log.error("solr request error",e);
			}
			solr.shutdown();
		} catch (IOException e) {	
			log.error("solr request error",e);

		}

		

	}

	public static List<String> solrQuery(String keyword) {
		String urlString = url;
		SolrServer solr = new HttpSolrServer(urlString);

		SolrQuery para = new SolrQuery();

		para.set("q", "attr_content:" + keyword);
		para.set("fl", "id");
		QueryResponse query;
		List<String> queryRes = new ArrayList<String>();
		try {
			query = solr.query(para);
 
			log.info("query keywords:" + keyword + ",result size:" + query.getResults().getNumFound());
			if (query.getResults().getNumFound() > 0) {
				for (int i = 0; i < query.getResults().getNumFound(); i++) {
					queryRes.add(query.getResults().get(i).get("id") + "");
					log.info("result key:" + query.getResults().get(i).get("id") + "");
 
				}
			}

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			solr.shutdown();
		}
		return queryRes;
	}
	public static void main(String[] args){
		SolrUtil.indexFilesSolrCell("E:/TestLucene/files/test1.pdf", "E:/TestLucene/files/test1.pdf","pdf");
		SolrUtil.solrQuery("student");
	}
}
