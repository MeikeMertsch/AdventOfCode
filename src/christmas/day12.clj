(ns christmas.day12)

(defn init-pots [pots]
	(str "..." pots "..."))

(defn re-pot [pots instructions]
	(->> (partition 5 1 (init-pots pots))
		 ;(map #(apply str %))
		 (map #(get instructions % "."))))
	

(defn day12 [init-pots instructions it]
	(reduce (fn [[pots _] idx] (vector (re-pot pots instructions) idx)) 
		[init-pots 0] (range 0 it)))

(defn count-pots [init-pots instructions gen]
	(->> (day12 init-pots instructions gen)
		 first
		 (#(zipmap (range (- 0 gen) (+ gen 1000)) %))
		 ;first
		 (remove #(= \. (last %)))
		 (map first)
		 (apply +)
		 ))
	