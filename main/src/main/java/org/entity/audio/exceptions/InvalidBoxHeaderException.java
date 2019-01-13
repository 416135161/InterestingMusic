package org.entity.audio.exceptions;

/**
 * Thrown if when trying to read box id the length doesn'timer make any sense
 */
public class InvalidBoxHeaderException extends RuntimeException
{
    public InvalidBoxHeaderException(String message)
    {
        super(message);
    }
}
