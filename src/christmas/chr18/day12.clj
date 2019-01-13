(ns christmas.chr18.day12)

(defn init-pots [pots]
	(concat "   " pots "   "))

(defn re-pot [pots instructions idx]
	;(spit "resources/Output12" (str idx (apply str pots) "\n") :append true)
	(->> (partition 5 1 (init-pots pots))
		 (map #(get instructions % \space))))
	
(defn day12 [init-pots instructions it]
	(reduce (fn [[pots _] idx] (vector (re-pot pots instructions idx) idx)) 
		[init-pots 0] (range 0 it)))

(defn do-math [pots gen]
	(->> (zipmap (range (- 0 gen) (+ gen 1000)) pots)
		 (remove #(= \space (last %)))
		 (map first)
		 (apply +)))

(defn count-pots [init-pots instructions gen]
	(-> (day12 init-pots instructions gen)
		first
		(do-math gen)))