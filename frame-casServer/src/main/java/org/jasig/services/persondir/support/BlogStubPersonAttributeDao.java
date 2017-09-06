package org.jasig.services.persondir.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.services.persondir.IPersonAttributes;


public class BlogStubPersonAttributeDao extends StubPersonAttributeDao {

	@Override
	public IPersonAttributes getPerson(String uid) {
		Map<String, List<Object>> attributes = new HashMap<String, List<Object>>();
		attributes.put("userid", Collections.singletonList((Object)uid));
		attributes.put("cnblogUsername", Collections.singletonList((Object)"http://www.cnblogs.com/vhua"));
		attributes.put("cnblogPassword", Collections.singletonList((Object)"123456"));
		attributes.put("test", Collections.singletonList((Object)"test"));
		return new AttributeNamedPersonImpl(attributes);
	}
	
}
