package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Server.class)
public abstract class Server_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile ListAttribute<Server, Project> configuredProjects;
	public static volatile SingularAttribute<Server, String> description;
	public static volatile SingularAttribute<Server, String> name;
	public static volatile SingularAttribute<Server, String> targetdir;

}
