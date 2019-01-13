(ns christmas.chr18.day25
	(:require [clojure.set :as s]))

(defn distance [pos1 pos2]
	(->> (map - pos1 pos2)
		 (map #(Math/abs %))
		 (apply +)))

(defn collect-if-close [pos reachables pos2]
	(if (< (distance pos pos2) 4) 
		(conj reachables [pos pos2]) 
		reachables))

(defn reachables [pos positions]
	(->> (remove #{pos} positions)
		 (reduce (partial collect-if-close pos) [])))
	
(defn all-pairs [positions]
	(mapcat #(reachables % positions) positions))

(defn in-constellation [all-constellations pos]
	(some #(if (contains? % pos) %) all-constellations))

(defn find-constellations [all-constellations pair]
	(map (partial in-constellation all-constellations) pair))

(defn add-to-constellation [all-constellations found-constellations pair]
	(let [constellation (some identity found-constellations)]
		(-> (remove #{constellation} all-constellations)
			(conj (apply conj constellation pair)))))

(defn merge-constellations [all-constellations found-constellations]
	(-> (remove (into #{} found-constellations) all-constellations)
		(conj (apply s/union found-constellations))))

(defn treat-pair [all-constellations [pos pos2 :as pair]]
	;(time 
	(let [sets (map (partial in-constellation all-constellations) pair)]
		(cond
			(= sets [nil nil]) (conj all-constellations #{pos pos2})
			(= (first sets) (last sets)) all-constellations
			(some nil? sets) (add-to-constellation all-constellations sets pair)
			:rest (merge-constellations all-constellations sets)))) ;)

(defn day25 [positions]
	(->> (reduce treat-pair [] (all-pairs positions))
		 (#(+ (count %) (- (count positions) (apply + (map count %)))))))
	


