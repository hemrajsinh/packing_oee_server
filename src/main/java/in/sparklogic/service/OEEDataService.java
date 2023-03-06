package in.sparklogic.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.sparklogic.params.OEEDataDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OEEDataService {

	@PersistenceContext
	private EntityManager entityManager;


	@Transactional
	public List<OEEDataDTO> getData() {

		List<OEEDataDTO> listData = new ArrayList<OEEDataDTO>();
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			Query query = entityManager.createNativeQuery("SELECT dateandtime, tagindex, convert(varchar(255), tagdescription) as tagdescription, val FROM line1_mespack_oeedashboard");
			// query.setParameter(2, version);

			List<Object[]> list = query.getResultList();

			for (int i = 0; i < list.size()-1; i++) {
				Object[] objects = (Object[]) list.get(i);
				OEEDataDTO oeeData = new OEEDataDTO();
				
				if (!StringUtils.isEmpty(objects[0])) {
					Timestamp cts = (Timestamp) objects[0];
					oeeData.setDateAndTime(cts);
				}
				oeeData.setTagIndex((short) objects[1]);
				oeeData.setTagDescription((String) objects[2]);
				oeeData.setVal((String) objects[3]);
				
				listData.add(oeeData);
			}
			return listData;
		} catch (NoResultException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listData;
	}

}
