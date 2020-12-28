(ns christmas.chr20.day11
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn generate-entry [row-number thing column-number]
  ({\L [row-number column-number] \. nil} thing))

(defn generate-line [line row-number]
  (map #(generate-entry row-number %1 %2) line (range (count line))))

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (#(mapcat (fn [a b] (generate-line b a)) (range (count %)) %))
       (filter some?)))

(def dirs [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]])

(defn neighbor [coords]
  (map (partial map + coords) dirs))

(defn tick-space [game coords]
  (->> (neighbor coords)
       (map game)
       (filter true?)
       (count)
       (#(if (= 0 %)
           (vector coords true)
           (if (< % 4)
             (find game coords)
             (vector coords false))))))

(defn tick [game]
  (->> (mapcat (partial tick-space game) (keys game))
       (apply hash-map)))

(defn day11 [input]
  (->> (parsefile input)
       (#(zipmap % (repeat false)))
       (iterate tick)
       (map #(count (filter true? (vals %))))
       (partition 2 1)
       (drop-while #(not= (first %) (last %)))
       (ffirst)))

(defn max-coords [game]
  (->> (apply map vector game)
       (mapv (partial apply max))))

(defn walk [seats [size-x size-y] coords dir]
  (->> (iterate (partial map + dir) coords)
       (rest)
       (take-while (fn [[x y]] (and (<= 0 x size-x)
                                    (<= 0 y size-y))))
       (t/find-first (set seats))))

(defn visible [seats size coords]
  (map (partial walk seats size coords) dirs))

(defn init [seats]
  (hash-map :size (max-coords seats) :seats (set seats) :occupied #{}))

(defn occupied? [seats size occupied seat]
  (->> (visible seats size seat)
       (keep occupied)
       (count)
       (#(if (occupied seat)
           (> 5 %)
           (= 0 %)))))

(defn occupy [{:as game :keys [occupied size seats]}]
  (->> (filter (partial occupied? seats size occupied) seats)
       (set)
       (assoc game :occupied)))

(defn create-row [size-x occupied seats row-nr]
  (->> (range (inc size-x))
       (map #(vector row-nr %))
       (map #(if (occupied %) "#" (if (seats %) "L" ".")))
       (apply str)))

(defn to-visible [{:as game :keys [occupied size seats]}]
  (->> (range (inc (last size)))
       (map (partial create-row (first size) occupied seats))))

(defn day11b [input]
  (->> (parsefile input)
       (init)
       (iterate occupy)
       (map #(count (:occupied %)))
       (partition 2 1)
       (drop-while #(not= (first %) (last %)))
       (ffirst)))

