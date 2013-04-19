package Data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Converter {
	public static void main(String [] args){
		Converter c = new Converter();
		/*
		 * The following code integrates all raw data in one
		 */
		/*
		c.combine("DataSet/A/A.feature", "DataSet/A/A.label", "DataSet/All.combine.arff",false,false);
		c.combine("DataSet/B/B.feature", "DataSet/B/B.label", "DataSet/All.combine.arff",true,false);
		c.combine("DataSet/C/C.feature", "DataSet/C/C.label", "DataSet/All.combine.arff",true,false);
		c.combine("DataSet/D/D.feature", "DataSet/D/D.label", "DataSet/All.combine.arff",true,false);
		c.combine("DataSet/E/E.feature", "DataSet/E/E.label", "DataSet/All.combine.arff",true,false);
		*/
		/*
		 * The following code convert raw data into single arff file
		 */
		/*
		c.combine("DataSet/A/A.feature", "DataSet/A/A.label", "DataSet/A/A.combine.arff",false,false);
		c.combine("DataSet/B/B.feature", "DataSet/B/B.label", "DataSet/B/B.combine.arff",false,false);
		c.combine("DataSet/C/C.feature", "DataSet/C/C.label", "DataSet/C/C.combine.arff",false,false);
		c.combine("DataSet/D/D.feature", "DataSet/D/D.label", "DataSet/D/D.combine.arff",false,false);
		c.combine("DataSet/E/E.feature", "DataSet/E/E.label", "DataSet/E/E.combine.arff",false,false);
		*/
		/*
		 * The following code converts test data into arff format
		 */
		c.combine("DataSet/XYZ/X.feature", null, "DataSet/XYZ/X.feature.arff",false,false);
		//c.combine("DataSet/XYZ/Y.feature", null, "DataSet/XYZ/Y.feature.arff",false,false);
		//c.combine("DataSet/XYZ/Z.feature", null, "DataSet/XYZ/Z.feature.arff",false,false);
		/*
		 * The following code converts raw data into relational arff format
		 */
		//c.combine("DataSet/A/A.feature", "DataSet/A/A.label", "DataSet/A/A.combine.relation.arff",false,true);
	}
	public boolean combine(String feature_path,String label_path,String combine_path,boolean append,boolean relational){
		FileReader featureReader = null;
		FileReader labelReader = null;
		try {
			featureReader = new FileReader(feature_path);
			if(label_path != null) labelReader = new FileReader(label_path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		FileWriter writer = null;
		try {
			writer = new FileWriter(combine_path,append);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				featureReader.close();
				if(labelReader != null) labelReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		// create a scanner from the data file

		Scanner featureScanner = new Scanner(featureReader);
		Scanner labelScanner = null;
		if(labelReader != null){
			labelScanner = new Scanner(labelReader);
		}
		// repeat while there is a next item to be scanned
		try {
			if(!append){
				writer.write("@RELATION SensorData\r\n");
				
				if(relational){
					writer.write("@ATTRIBUTE ID	NUMERIC\r\n");
					writer.write("@ATTRIBUTE bag relational\r\n");
				}else{
					if(labelReader != null) writer.write("@ATTRIBUTE timestamp	NUMERIC\r\n@ATTRIBUTE heartbeatrate	NUMERIC\r\n");
				}
				
				writer.write("@ATTRIBUTE S1-0		NUMERIC\r\n@ATTRIBUTE S1-1		NUMERIC\r\n@ATTRIBUTE S1-2		NUMERIC\r\n@ATTRIBUTE S1-3		NUMERIC\r\n@ATTRIBUTE S1-4		NUMERIC\r\n@ATTRIBUTE S1-5		NUMERIC\r\n@ATTRIBUTE S1-6		NUMERIC\r\n@ATTRIBUTE S1-7		NUMERIC\r\n@ATTRIBUTE S1-8		NUMERIC\r\n@ATTRIBUTE S1-9		NUMERIC\r\n@ATTRIBUTE S1-10		NUMERIC\r\n@ATTRIBUTE S1-11		NUMERIC\r\n@ATTRIBUTE S1-12		NUMERIC\r\n");
				writer.write("@ATTRIBUTE S2-0		NUMERIC\r\n@ATTRIBUTE S2-1		NUMERIC\r\n@ATTRIBUTE S2-2		NUMERIC\r\n@ATTRIBUTE S2-3		NUMERIC\r\n@ATTRIBUTE S2-4		NUMERIC\r\n@ATTRIBUTE S2-5		NUMERIC\r\n@ATTRIBUTE S2-6		NUMERIC\r\n@ATTRIBUTE S2-7		NUMERIC\r\n@ATTRIBUTE S2-8		NUMERIC\r\n@ATTRIBUTE S2-9		NUMERIC\r\n@ATTRIBUTE S2-10		NUMERIC\r\n@ATTRIBUTE S2-11		NUMERIC\r\n@ATTRIBUTE S2-12		NUMERIC\r\n");
				writer.write("@ATTRIBUTE S3-0		NUMERIC\r\n@ATTRIBUTE S3-1		NUMERIC\r\n@ATTRIBUTE S3-2		NUMERIC\r\n@ATTRIBUTE S3-3		NUMERIC\r\n@ATTRIBUTE S3-4		NUMERIC\r\n@ATTRIBUTE S3-5		NUMERIC\r\n@ATTRIBUTE S3-6		NUMERIC\r\n@ATTRIBUTE S3-7		NUMERIC\r\n@ATTRIBUTE S3-8		NUMERIC\r\n@ATTRIBUTE S3-9		NUMERIC\r\n@ATTRIBUTE S3-10		NUMERIC\r\n@ATTRIBUTE S3-11		NUMERIC\r\n@ATTRIBUTE S3-12		NUMERIC\r\n");
				if(relational){
					writer.write("@end bag\r\n");
				}
				writer.write("@ATTRIBUTE CLASS		{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}\r\n");
				writer.write("@data\r\n");
			}
			int current_line = 1;
			int prev_class = -1;
			String prev_combine = null;
			String combine = null;
			while (featureScanner.hasNext()) {

				String feature = featureScanner.nextLine();
				
				if(labelReader == null){
					combine = feature+",?";
				}else{
					String label = labelScanner.nextLine();
					combine = feature+","+label;
				}
				//System.out.println(combine);
				int second_comma_index = combine.indexOf(",",combine.indexOf(",")+1);
				if(relational){
					
					int last_comma_index = combine.lastIndexOf(",");
					int current_class = Integer.valueOf(combine.substring(last_comma_index+1));
					if(prev_class != current_class){
						if(prev_combine != null){
							writer.write("\","+prev_combine.substring(prev_combine.lastIndexOf(",")+1)+"\r\n");
						}
						writer.write((current_line++)+",\""+combine.substring(second_comma_index+1,last_comma_index));
					}else{
						writer.write("\\r\\n"+combine.substring(second_comma_index+1,last_comma_index));
					}
					prev_class = current_class;
					prev_combine = combine;
				}else{
					if(labelReader != null){
						writer.write(combine+"\r\n");
					}else{
						writer.write(combine.substring(second_comma_index+1)+"\r\n");
					}
				}
			}
			if(relational && (prev_combine != null)){
				writer.write("\","+prev_combine.substring(prev_combine.lastIndexOf(",")+1)+"\r\n");
			}
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}finally{
			featureScanner.close();
			if(labelScanner != null) labelScanner.close();
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		return true;
	}
	
}
