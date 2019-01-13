package org.entity.audio.real;

import org.entity.audio.generic.GenericTag;
import org.entity.tag.FieldDataInvalidException;
import org.entity.tag.FieldKey;
import org.entity.tag.KeyNotFoundException;
import org.entity.tag.TagField;

public class RealTag extends GenericTag
{
    public String toString()
    {
        String output = "REAL " + super.toString();
        return output;
    }

    public TagField createCompilationField(boolean value) throws KeyNotFoundException, FieldDataInvalidException
    {
        return createField(FieldKey.IS_COMPILATION,String.valueOf(value));
    }
}
