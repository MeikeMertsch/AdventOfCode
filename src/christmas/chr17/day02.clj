(ns christmas.chr17.day02)


(defn calculate-row [row]
	(- (apply max row) (apply min row)))


(defn exercise02 [input]
	(->> (map calculate-row input)
		 (apply +)))

;------------------

(defn find-pairs [row number]
	(map #(vector (rem %1 %2) %1 %2) row (repeat number)))

(defn deal-with-row [row]
	(->> (mapcat #(find-pairs row %) row)
		 (filter #(and (= 0 (first %))
		 	           (not= (second %) (last %))))
		 (map #(/ (second %)(last %)))
		 first
	))

(defn exercise02b [input]
	(->> (map deal-with-row input)
		 (apply +)
		 ))