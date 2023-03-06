/*package in.co.gfl.config;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
public class LocalDateTimeConverter implements AttributeConverter < LocalDateTime, Timestamp > {
 @Override
 public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
  return attribute != null ? Timestamp.valueOf(attribute) : null;
 }
 @Override
 public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
  return dbData != null ? dbData.toLocalDateTime() : null;
 }
}*/




package in.sparklogic.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {
	
	@Override
	public Date convertToDatabaseColumn(LocalDateTime data) {
		
		if (data != null) {
			ZoneId zoneId = ZoneId.systemDefault();
	        LocalDateTime localDateTime = data;
	        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
	 
	        return Date.from(zonedDateTime.toInstant());
	        
//			return Date.from(data.atZone(ZoneId.of(ZoneId.SHORT_IDS.get("IST"))).toInstant());
			
		}
		return null;
	}
	
	@Override
	public LocalDateTime convertToEntityAttribute(Date data) {
		if (data != null) {
			Instant instant = data.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			return instant.atZone(zoneId).toLocalDateTime();
		}
		return null;
	}
	
}