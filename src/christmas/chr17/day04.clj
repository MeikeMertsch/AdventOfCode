(ns christmas.chr17.day04)

(defn day04 [input]
	(->> (map #(map sort %) input)
		 (map #(apply distinct? %))
		 (remove false?)
		 count))