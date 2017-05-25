package com.lxz.hollow;

import com.beust.jcommander.internal.Lists;
import com.netflix.hollow.core.write.HollowBlobWriter;
import com.netflix.hollow.core.write.HollowWriteStateEngine;
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by xiaolezheng on 17/2/23.
 */
public class HollowTest {
    public static void main(String[] args)throws Exception{
        List<Movie> movies = Lists.newArrayList(
                new Movie(1, "The Matrix", 1999),
                new Movie(2, "Beasts of No Nation", 2015),
                new Movie(3, "Pulp Fiction", 1994)
        );

        HollowWriteStateEngine writeEngine = new HollowWriteStateEngine();
        HollowObjectMapper mapper = new HollowObjectMapper(writeEngine);

        for(Movie movie : movies)
            mapper.add(movie);

        OutputStream os = null;
        HollowBlobWriter writer = new HollowBlobWriter(writeEngine);
        writer.writeSnapshot(os);
    }
}
