import java.util.Arrays;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
class InformationRetrievalApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//PositionalIndex pi = new PositionalIndex("test/");//System.out.println("There are " + pi.positionalIndex.keySet().size() + " terms in the dictionary.");
		//pi.checkPostingsOrder();//System.out.println("Postings order checked");
		//pi.checkDuplicatePosition();//System.out.println("Duplicate position checked");
		//pi.checkDocumentFrequency();System.out.println("Document frequency checked");
		//pi.printDocumentAndPositionalIndex("1915_Philadelphia_Phillies_season.txt");
		//for(String document : pi.documentIDToName.values()) {
		//	pi.printDocumentAndPositionalIndex(document);
		//}System.out.println("positional index checked");
		/*String query = "Brooklyn Robins in 1916";
		Map<Integer, Double> collectionVSScore = pi.VSScore(query);System.out.println("VSScore done");
		for(String document : pi.documentIDToName.values()) {System.out.print(document);
			if(!collectionVSScore.containsKey(pi.getDocumentID(document)))
				if(pi.VSScore(query, document) != 0)
					throw new IllegalArgumentException("vsscore:" + pi.VSScore(query, document));
				else {
					System.out.println(document + " has VSScore " + pi.VSScore(query, document));
					continue;
				}
			if(collectionVSScore.get(pi.getDocumentID(document)).doubleValue() != pi.VSScore(query, document)) {
				throw new IllegalArgumentException("Values do not agree. (" + collectionVSScore.get(pi.getDocumentID(document)).doubleValue()+ ", " + pi.VSScore(query, document) + ")");
			}
		}*?
		/*List<Integer> p = new ArrayList<Integer>();
		List<Integer> r = new ArrayList<Integer>();
		p.add(6);p.add(18);p.add(21);p.add(46);
		r.add(5);r.add(9);r.add(11);r.add(20);r.add(34);
		//p.add(6);p.add(18);p.add(21);p.add(46);
		//r.add(55);r.add(59);r.add(61);r.add(70);r.add(74);
		//p.add(36);p.add(38);p.add(41);p.add(46);
		//r.add(5);r.add(9);r.add(11);r.add(20);r.add(34);
		System.out.println(new PositionalIndex(17).getMinimumElementWiseDistance(r, p));*/
		String folderName = "data/";
		QueryProcessor qp = new QueryProcessor(folderName);
		String[] querys = {"chicago", "new york", "chicago cubs season", "1923 washington senators season", "colored world series in 1924"};
		for(String q : querys) {
			System.out.println("The top 10 relevant documents in " + folderName + " for " + q + ": " + Arrays.toString(qp.topKDocs(q, 10).toArray()));
			//System.out.println("The top 10 relevant docuemtns: " + Arrays.toString(qp.alternative(q, 10).toArray()));
		}
		//String query = querys[4];
		//System.out.println("The top 10 relevant docuemtns in " + folderName + " for " + query + ": " + Arrays.toString(qp.topKDocs(query, 10).toArray()));
	}

}
