package allen.controller;

public class ObjectNotFoundException extends RuntimeException {
	ObjectNotFoundException(Long id, String obj) {
		super("Could not find " + obj + " " + id);
	}

}
