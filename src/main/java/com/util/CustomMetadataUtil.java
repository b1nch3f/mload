/*
*Copyright 2016 the original author or authors.

*Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

*The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.util;

import com.sforce.soap.metadata.*;
import com.sforce.soap.metadata.Error;
import java.util.ArrayList;

/**
 * Metadata API Util Class
 */
public class CustomMetadataUtil {
    private MetadataConnection metadataConnection;

    public static String run(String objectname, ArrayList<String> header, ArrayList<ArrayList<String>> data) throws Exception {
        MetadataConnection metadataConnection = CredentialsManager.mdConnection;
        Metadata [] records = new Metadata[data.size()];
                
        for(int index = 0; index < data.size(); ++index) {
            ArrayList<String> item = data.get(index);
            // MeetupApiKey
            String fullName = objectname+"."+item.get(0);
            String label = item.get(1);

            CustomMetadata cm = new CustomMetadata();
            cm.setFullName(fullName);
            cm.setLabel(label);

            ArrayList<CustomMetadataValue> fieldValues = new ArrayList<CustomMetadataValue>();
            
            for(int var = 2; var <= header.size() - 1; ++var) {
                CustomMetadataValue fieldVal = new CustomMetadataValue();
                fieldVal.setField(header.get(var));
                fieldVal.setValue(item.get(var));
                fieldValues.add(fieldVal);
            }

            CustomMetadataValue values[] = new CustomMetadataValue [fieldValues.size()];
            
            for(int v = 0; v < fieldValues.size(); ++v) {
                values[v] = fieldValues.get(v);
            }

            cm.setValues(values);
            records[index] = cm;
        }

        UpsertResult[] results = metadataConnection
                .upsertMetadata(records);

        for (UpsertResult r : results) {
            if (r.isSuccess()) {
                return "Success !";
            }
        }
        
        return "Error";
    }
}