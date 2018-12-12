(ns christmas.day11)

(defn digit [number]
	(- (quot number 100)
	   (* 10 (quot number 1000))))

(defn sqare-fn [func start end]
	(let [length (range start (inc end))]
		(map func (cycle length) 
				  (mapcat #(take (- (inc end) start) (repeat %)) length))))
	
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
	(->> (map #(take size (drop (dec column) %)) (take size (drop (dec row) board)))
		 (apply concat)
		 (apply +)))

(defn figure-out-max [size row some-map]
	(->> (apply max (keys some-map))
		 (#(vector % size (get some-map %) row))))

(defn row-max [row size board]
	(->> (map #(square-power [% row] size board) (range 0 (- 301 size)))
         (#(interleave % (range)))
         (apply assoc {})
		 (figure-out-max size row)))

(defn size-max [size board]
	(->> (map #(row-max % size board) (range 0 (- 301 size)))
		 (sort-by first >)
		 first
	))

(defn top [grid]
	(->> (map #(size-max % (powered-board grid)) (range 12 300))
		 (sort-by first >)
		 first
	))
