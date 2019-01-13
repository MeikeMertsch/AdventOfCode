(ns christmas.chr17.day01)

(defn parse [char1]
	(int (bigint (str char1))))


(defn exercise01 [input]
	(->> (concat input (take 1 input))
		 (partition-by identity)
		 (remove #(= 1 (count %)))
		 (mapcat rest)
		 (map parse)
		 (apply +)))
	
;------------------------------------

(defn exercise01b [input]
	(let [half-length (/ (count input) 2)]
	(->> (take half-length input)
		 (concat input)
		 (drop half-length)
		 (map vector input)
		 (remove #(not= (first %) (last %)))
		 (map (comp parse first))
		 (apply +)
		))

)
