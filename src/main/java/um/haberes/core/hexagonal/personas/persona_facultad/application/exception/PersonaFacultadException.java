package um.haberes.core.hexagonal.personas.persona_facultad.application.exception;

public class PersonaFacultadException extends RuntimeException {

    private static final long serialVersionUID = -2734681989133181644L;

    public PersonaFacultadException(String message) {
        super(message);
    }

    public static PersonaFacultadException missingData() {
        return new PersonaFacultadException("legajoId and facultadId are required");
    }

    public static PersonaFacultadException alreadyExists(Long legajoId, Integer facultadId) {
        return new PersonaFacultadException(
                "PersonaFacultad already exists for legajoId " + legajoId + " and facultadId " + facultadId);
    }
}
