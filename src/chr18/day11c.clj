(ns christmas.day11c)

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

(defn powered-board [grid]
	(zipmap (sqare-fn vector 1 300)
			(sqare-fn (fn [x y] (power [x y] grid)) 1 300)))

(defn shift-cell [x y m n]
	[(+ x m)
	 (+ y n)])
	

(defn total-power [[x y] grid size board]
    (->> (map #(get board %) (sqare-fn #(shift-cell x y %1 %2) 0 (dec size)))
		 (apply +)
		 (#(vector % [x y]))))
		
(defn figure-out-max [size some-map]
	(->> (apply max (keys some-map))
		 (#(vector % size (get some-map %)))))


(defn calculate-board [grid size board]
		(->> (sqare-fn #(total-power [%1 %2] 
					grid 
					size 
					board) 1 (- 301 size))
			(apply concat)
			(apply assoc {})
			(figure-out-max size)
		))

(defn top [grid]
	(let [board (powered-board grid)]
	(->> (map #(calculate-board grid % board) (range 51 55))
		 (sort-by first >)
		 first)))
			