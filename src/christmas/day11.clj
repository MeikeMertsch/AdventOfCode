(ns christmas.day11)

(defn digit [number]
	(- (quot number 100)
	   (* 10 (quot number 1000))))
	
(defn power [[x y] grid]
	(let [rack-id (+ 10 x)]
	(-> (* y rack-id)
		(+ grid)
		(* rack-id)
		digit
		(- 5))))

(defn powered-row [row grid]
	(map #(power [% row] grid) (range 1 301)))

(defn powered-board [grid]
	(map #(powered-row % grid) (range 1 301)))

(defn square-power [[column row] size board]
	(->> (map #(take size (drop (dec column) %)) 
			   (take size (drop (dec row) board)))
		 (apply concat) ;mapcat ^
		 (apply +))) ;give out as (#(vector % [column row]))

(defn figure-out-max [size row some-map]
	(->> (apply max (keys some-map))
		 (#(vector % size (get some-map %) row))))

(defn row-max [row size board]
	(->> (map #(square-power [% row] size board) (range 0 (- 301 size)))
         (#(interleave % (range))) ; drop
         (apply assoc {}) ; drop
		 (figure-out-max size row))) ; instead sort-by first >

(defn size-max [size board]
	(->> (map #(row-max % size board) (range 0 (- 301 size)))
		 (sort-by first >)
		 first))

(defn top [grid]
	(->> (map #(size-max % (powered-board grid)) (range 12 300))
		 (sort-by first >)
		 first))
