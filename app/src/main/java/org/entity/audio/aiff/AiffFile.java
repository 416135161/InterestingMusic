package org.entity.audio.aiff;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import org.entity.audio.AudioFile;
import org.entity.audio.exceptions.InvalidAudioFrameException;
import org.entity.audio.exceptions.ReadOnlyFileException;
import org.entity.tag.TagException;

public class AiffFile extends AudioFile {

    /** A static DateFormat object for generating ISO date strings 
     */
    public final static SimpleDateFormat ISO_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    /**
     * Creates a new empty AiffFile that is not associated with a
     * specific file.
     */
    public AiffFile()   {
        
    }
    
    
    /**
     * Creates a new MP3File datatype and parse the tag from the given filename.
     *
     * @param filename AIFF file
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     * @throws org.entity.audio.exceptions.ReadOnlyFileException
     * @throws org.entity.audio.exceptions.InvalidAudioFrameException
     */
    public AiffFile(String filename) throws 
            IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException    {
        this(new File(filename));
    }
    
    /**
     * Creates a new MP3File datatype and parse the tag from the given file
     * Object.
     *
     * @param file MP3 file
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     * @throws org.entity.audio.exceptions.ReadOnlyFileException
     * @throws org.entity.audio.exceptions.InvalidAudioFrameException
     */
    public AiffFile(File file) 
            throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException    {
        this (file, true);
    }
    
    public AiffFile(File file, boolean readOnly) 
            throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        RandomAccessFile newFile = null;
        try
        {
            logger.setLevel(Level.FINEST);
            logger.fine("Called AiffFile constructor on " + file.getAbsolutePath());
            this.file = file;

            //Check File accessibility
            newFile = checkFilePermissions(file, readOnly);
            audioHeader = new AiffAudioHeader();
            //readTag();

        }
        finally
        {
            if (newFile != null)
            {
                newFile.close();
            }
        }
    }
    
    public AiffAudioHeader getAiffAudioHeader () {
        return (AiffAudioHeader) audioHeader;
    }

}
